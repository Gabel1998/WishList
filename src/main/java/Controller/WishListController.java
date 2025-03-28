package Controller;

import DTO.WishListDTO;
import Model.WishList;
import Service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


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
        //konverterer wishlist til wishlistDTO
        WishListDTO wishListDTO = new WishListDTO(wishList.getWishListId(), wishList.getName());
        try {
            wishListService.createWishList(wishListDTO);
            model.addAttribute("message", "Ønskeseddel blev oprettet: " + wishList.getWishListId());
        } catch (Exception e) {
            model.addAttribute("message", "Fejl ved oprettelse: " + e.getMessage());
        }
        return "redirect:/" + userId + "/wishlist";
    }
}
