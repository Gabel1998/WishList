/// ============================================
/// =   JDBC-metoder til håndtering af items   =
/// ============================================
package com.example.wishlist.service;

import com.example.wishlist.dto.ItemDTO;
import com.example.wishlist.dto.WishListDTO;
import com.example.wishlist.model.Item;
import com.example.wishlist.model.SharedItem;
import com.example.wishlist.model.WishList;
import com.example.wishlist.repository.SharedItemRepository;
import com.example.wishlist.repository.WishListRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;
    private final SharedItemRepository sharedItemRepository;

    public WishListService(WishListRepository wishListRepository, SharedItemRepository sharedItemRepository) {
        this.wishListRepository = wishListRepository;
        this.sharedItemRepository = sharedItemRepository;
    }

    public void createWishList(WishListDTO wishListDTO) {
        wishListRepository.insertWishList(wishListDTO);
    }


    public void addItemToWishList(int wishlistId, ItemDTO itemDTO) {
        Item item = new Item();
        item.setName(itemDTO.getName());
        item.setDescription(itemDTO.getDescription());
        item.setPrice(itemDTO.getPrice());
        item.setUrl(itemDTO.getUrl());
        item.setReserved(false); // nyt produkt er altid ikke-reserveret
        item.setWishList(new WishList(wishlistId)); // relation til ønskeseddel

        wishListRepository.insertItem(item);
    }


    public void updateItem(int itemId, ItemDTO itemDTO) {
        Item item = wishListRepository.findItemById(itemId);

        if (item != null) {
            item.setName(itemDTO.getName());
            item.setDescription(itemDTO.getDescription());
            item.setPrice(itemDTO.getPrice());
            item.setUrl(itemDTO.getUrl());
            item.setReserved(itemDTO.isReserved());

            wishListRepository.updateItem(item);
        } else {
            throw new RuntimeException("Ønske med ID " + itemId + " findes ikke.");
        }
    }

    public void deleteItem(int itemId) {
        Item item = wishListRepository.findItemById(itemId);

        if (item != null) {
            wishListRepository.deleteItem(itemId);
        } else {
            throw new RuntimeException("Ønske med ID " + itemId + " findes ikke.");
        }
    }

    public void reserveItem(int reservationId, int rsvItemsId) {
        Item item = wishListRepository.findItemById(rsvItemsId);

        if (item != null) {
            item.setReserved(true);
            wishListRepository.updateItem(item);
        } else {
            throw new RuntimeException("Ønske med ID " + rsvItemsId + " findes ikke.");
        }
    }

    // Tilføjet metode for at hente alle ønskesedler for én bruger
    public List<WishListDTO> getAllWishListsByUser(int userId) {
        List<WishList> wishLists = wishListRepository.findWishListsByUserId(userId);
        List<WishListDTO> dtos = new ArrayList<>();

        for (WishList wl : wishLists) {
            WishListDTO dto = new WishListDTO(wl.getWishListId(), wl.getName(), wl.getShare_token());
            dto.setUserId(userId); // hvis relevant
            dtos.add(dto);
        }

        return dtos;
    }

    //read-only ønskeliste

    public String shareWishlist(long wishlistId) {
        String shareToken = wishListRepository.findLatestShareTokenForWishlist((int) wishlistId);

        if (shareToken == null) {
            shareToken = wishListRepository.insertSharedWishlistAndReturnToken(wishlistId);
            Long sharedWishlistId = wishListRepository.findSharedWishlistIdByToken(shareToken);
            wishListRepository.copyItemsToSharedItems(wishlistId, sharedWishlistId);
        }

        // 5. Returnér token uanset hvad
        return shareToken;
    }


    public WishList findByShareToken(String shareToken) {
        return wishListRepository.findByShareToken(shareToken);
    }

    public List<SharedItem> getSharedItems(String shareToken) {
        Long sharedWishlistId = wishListRepository.findSharedWishlistIdByToken(shareToken);
        if (sharedWishlistId == null) {
            return null;
        }
        return sharedItemRepository.findSharedItemsBySharedWishlistId(sharedWishlistId); // ✅
    }

    public void reserveSharedItem(long sharedItemId) {
        // Simpel indsættelse i tb_reservations
        wishListRepository.insertReservation(sharedItemId);
    }

    public WishListDTO getWishListById(int wishlistId) {
        WishList wishList = wishListRepository.findWishListById(wishlistId);
        if (wishList == null) return null;

        List<Item> itemList = wishListRepository.findItemsByWishListId(wishlistId);
        List<ItemDTO> itemDTOs = new ArrayList<>();

        for (Item item : itemList) {
            ItemDTO dto = new ItemDTO();
            dto.setItemId(item.getItemId());
            dto.setName(item.getName());
            dto.setDescription(item.getDescription());
            dto.setPrice(item.getPrice());
            dto.setUrl(item.getUrl());

            // VIGTIGT: Vis aldrig reserved-status til ejeren
            dto.setReserved(false);

            itemDTOs.add(dto);
        }

        WishListDTO dto = new WishListDTO();
        dto.setWishListId(wishList.getWishListId());
        dto.setName(wishList.getName());
        String token = wishListRepository.findLatestShareTokenForWishlist(wishList.getWishListId());
        dto.setShareToken(token);
        dto.setUserId(wishList.getUser().getUserId());
        dto.setItems(itemDTOs);

        return dto;
    }

    public List<WishListDTO> getAllWishListsByUserEmail(String email) {
        List<WishList> wishLists = wishListRepository.findWishListsByUserEmail(email);
        List<WishListDTO> dtos = new ArrayList<>();

        for (WishList wl : wishLists) {
            WishListDTO dto = new WishListDTO();
            dto.setWishListId(wl.getWishListId());
            dto.setName(wl.getName());
            dto.setShareToken(wl.getShare_token());
            dto.setUserId(0); // Kan undlades hvis ikke nødvendig
            dtos.add(dto);
        }

        return dtos;
    }

    public int createWishListAndReturnId(WishList wishList, String email) {
        return wishListRepository.insertWishListAndReturnId(wishList, email);
    }

    public ItemDTO getItemById(int itemId) {
        Item item = wishListRepository.findItemById(itemId);

        if (item == null) {
            throw new RuntimeException("Ønske med ID " + itemId + " ikke fundet.");
        }

        ItemDTO dto = new ItemDTO();
        dto.setItemId(item.getItemId());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setPrice(item.getPrice());
        dto.setUrl(item.getUrl());
        dto.setReserved(item.getReserved());
        dto.setWishlistId((long) item.getWishlistId().getWishListId());
        return dto;
    }

    public String getLatestShareTokenForWishlist(int wishlistId) {
        return wishListRepository.findLatestShareTokenForWishlist(wishlistId);
    }

    public void deleteWishlist(int wishlistId) {
        // Først, slå alle tilknyttede items
        List<Item> items = wishListRepository.findItemsByWishListId(wishlistId);
        for (Item item : items) {
            wishListRepository.deleteItem(item.getItemId());
        }

        // Så, slet ønskesedlen
        wishListRepository.deleteWishlist(wishlistId);
    }
}


