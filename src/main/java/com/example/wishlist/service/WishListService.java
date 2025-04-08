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
        WishList wishList = wishListRepository.findWishListById(wishlistId);
        Item item = new Item();
        item.setWishList(wishList);
        item.setName(itemDTO.getName());
        item.setDescription(itemDTO.getDescription());
        item.setPrice(itemDTO.getPrice());
        item.setUrl(itemDTO.getLink());

        wishListRepository.insertItem(item);
    }

    public void updateItem(int itemId, ItemDTO itemDTO) {
        Item item = wishListRepository.findItemById(itemId);

        if (item != null) {
            item.setName(itemDTO.getName());
            item.setDescription(itemDTO.getDescription());
            item.setPrice(itemDTO.getPrice());
            item.setUrl(itemDTO.getLink());
            item.setReserved(itemDTO.isReserved());

            wishListRepository.updateItem(item);
        } else {
            throw new RuntimeException("Ønske med ID " + itemId + " findes ikke.");
        }
    }

    public void deleteItem(int itemId) {
        Item item = wishListRepository.findItemById(itemId);

        if(item != null) {
            wishListRepository.deleteItem(itemId);
        } else {
            throw new RuntimeException("Ønske med ID " + itemId + " findes ikke.");
        }
    }

    public void reserveItem(int reservationId, int rsvItemsId) {
        Item item = wishListRepository.findItemById(rsvItemsId);

        if(item != null) {
            item.setReserved(true);
            wishListRepository.updateItem(item);
        } else {
            throw new RuntimeException("Ønske med ID " + rsvItemsId + " findes ikke.");
        }
    }

    // 🆕 Tilføjet metode for at hente alle ønskesedler for én bruger
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
        wishListRepository.insertSharedWishlist(wishlistId);

        // Hent token der blev genereret af databasen
        String shareToken = wishListRepository.findShareTokenByOriginalWishlistId(wishlistId);
        // Hent ID på shared_wishlist
        Long sharedWishlistId = wishListRepository.findSharedWishlistIdByToken(shareToken);
        // Kopiér items
        wishListRepository.copyItemsToSharedItems(wishlistId, sharedWishlistId);

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

        if (wishList == null) {
            return null;
        }

        List<Item> itemList = wishListRepository.findItemsByWishListId(wishlistId);
        List<ItemDTO> itemDTOs = new ArrayList<>();

        for (Item item : itemList) {
            ItemDTO dto = new ItemDTO();
            dto.setItemId(item.getItemId());
            dto.setName(item.getName());
            dto.setDescription(item.getDescription());
            dto.setPrice(item.getPrice());
            dto.setLink(item.getUrl());
            dto.setReserved(item.getReserved());
            itemDTOs.add(dto);
        }

        WishListDTO dto = new WishListDTO();
        dto.setWishListId(wishList.getWishListId());
        dto.setName(wishList.getName());
        dto.setShareToken(wishList.getShare_token());
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



}


