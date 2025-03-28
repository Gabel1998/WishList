package Controller;

import Model.WishList;
import Service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;

@Controller
@RequestMapping("/")
public class WishListController {

    private final WishListService wishListService;

    @Autowired
    public WishListController(WishListService wishListService){
        this.wishListService = wishListService;
    }

    @PostMapping("/create")
    public String createWishList(@PathVariable("userId") int userId, @ModelAttribute WishList wishList, Model model){
        try {
            wishListService.createWishlist(wishList, userId);
            model.addAttribute("message", "Ønskeseddel blev oprettet: " + wishList.getWishListId());
        } catch (SQLException e) {
            model.addAttribute("message", "Fejl ved oprettelse: " + e.getMessage());
        }
        return "redirect:/" + userId + "/wishlist";
    }
}
