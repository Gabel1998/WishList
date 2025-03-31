/// ============================================
/// =   JDBC-metoder til håndtering af items   =
/// ============================================
package Service;

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
}

