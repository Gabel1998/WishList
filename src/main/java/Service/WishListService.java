/// ============================================
/// =   JDBC-metoder til håndtering af items   =
/// ============================================
package Service;

import DTO.ItemDTO;
import DTO.WishListDTO;
import Repository.WishListRepository;
import org.springframework.stereotype.Service;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;

    public WishListService(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    public void createWishList(WishListDTO wishListDTO) {
        wishListRepository.insertWishList(wishListDTO);
    }

    public void deleteItem(int id) {
        wishListRepository.deleteItem(id);
    }

    public void updateItem(int id, ItemDTO itemDTO) {
        wishListRepository.updateItem(id, itemDTO);
    }

    public void addItemToWishList(int id, ItemDTO itemDTO) {
        wishListRepository.addItem(id, itemDTO);
    }

    public void reserveItem(int reservation_id, int rsv_items_id) {
        wishListRepository.reserveItem(reservation_id, rsv_items_id);
    }

}

