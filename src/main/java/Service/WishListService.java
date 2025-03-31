/// ============================================
/// =   JDBC-metoder til håndtering af items   =
/// ============================================
package Service;

import DTO.ItemDTO;
import DTO.WishListDTO;
import Model.Item;
import Model.WishList;
import Repository.WishListRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;

    public WishListService(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
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

    //Generer en unik URL til read-only ønskelister
    public void generateUniqueURL(WishListDTO wishListDTO) {
        //Generér unique URL
        String uniqueURL = UUID.randomUUID().toString();

        //Opret objekt og set uniqueURL
        WishList wishList = new WishList();
        wishList.setName(wishListDTO.getName());
        wishList.setUniqueURL(uniqueURL);

        wishListRepository.insertWishList(wishList);
    }

    public WishList findByShareToken(String shareToken) {
        return wishListRepository.findByShareToken(shareToken);
    }
}

