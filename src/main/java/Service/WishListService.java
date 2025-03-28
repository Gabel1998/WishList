package Service;

import Model.WishList;
import Repository.WishListRepository;
import org.springframework.stereotype.Service;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;

    public WishListService(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    public void createWishlist(WishList wishList, int userId) {
        return wishListRepository.createWishList;
    }
}
