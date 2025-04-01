/// ============================================
/// =   JDBC-metoder til håndtering af items   =
/// ============================================
package Service;

import DTO.ItemDTO;
import DTO.WishListDTO;
import Model.Item;
import Model.SharedItem;
import Model.WishList;
import Repository.SharedItemRepository;
import Repository.WishListRepository;
import org.springframework.stereotype.Service;

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
        item.setQuantity(itemDTO.getQuantity());
        item.setLink(itemDTO.getLink());

        wishListRepository.insertItem(item);
    }

    public void updateItem(int itemId, ItemDTO itemDTO) {
        Item item = wishListRepository.findItemById(itemId);

        if(item != null) {
            item.setName(itemDTO.getName());
            item.setDescription(itemDTO.getDescription());
            item.setPrice(itemDTO.getPrice());
            item.setQuantity(itemDTO.getQuantity());
            item.setLink(itemDTO.getLink());

            //gem det updatede ønske ind i databasen igen
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

}


