/// ===========================================
/// =    Backend endpoints for ønskeliste     =
/// =     Tilføj, opdater og slet produkter   =
/// ===========================================
package Controller;

import DTO.ItemDTO;
import DTO.WishListDTO;
import Model.WishList;
import Model.SharedItem;
import Repository.WishListRepository;
import Service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/")

public class WishListController {
private final WishListService wishListService;

    @Autowired
    public WishListController(WishListService wishListService, WishListRepository wishListRepository){
        this.wishListService = wishListService;
    }

    // tilføj nyt produkt til ønskeliste
    @PostMapping("/wishlist/{id}/item")
    public ResponseEntity<String> addItem(@PathVariable("id") int wishlistId, @RequestBody ItemDTO itemDTO) {
        wishListService.addItemToWishList(wishlistId, itemDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Ønske tilføjet");
    }

    // opdater eksisterende produkt
    @PutMapping("/wishlist/item/{id}")
    public ResponseEntity<String> updateItem(@PathVariable("id") int itemId, @RequestBody ItemDTO itemDTO) {
        wishListService.updateItem(itemId, itemDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Ønske opdateret");
    }

    // slet produkt fra ønskeliste
    @DeleteMapping("/wishlist/item/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable("id") int itemId) {
        wishListService.deleteItem(itemId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Ønske slettet");
    }

    // reserver eksisterende produkt
    @PutMapping("/wishlist/item/{id}/reserve")
    public ResponseEntity<String> reserveItem(@PathVariable("id") int reservation_id, @RequestParam("item_id") int rsv_items_id) {
        wishListService.reserveItem(reservation_id, rsv_items_id);
        return ResponseEntity.status(HttpStatus.OK).body("Ønske reserveret");
    }

    @RequestMapping("/create/{userId}")    public String createWishList(@PathVariable("userId") int userId, @ModelAttribute WishList wishList, Model model){
        //konverterer wishlist til wishlistDTO
        WishListDTO wishListDTO = new WishListDTO(wishList.getWishListId(), wishList.getName(), wishList.getShare_token());
        try {
            wishListService.createWishList(wishListDTO);
            model.addAttribute("message", "Ønskeseddel blev oprettet: " + wishList.getWishListId());
        } catch (Exception e) {
            model.addAttribute("message", "Fejl ved oprettelse: " + e.getMessage());
        }
        return "redirect:/" + userId + "/wishlist";
    }

    @GetMapping("/view/{share_token}")
    public String viewReadOnly(@PathVariable String share_token, Model model) {
        List<SharedItem> items = wishListService.getSharedItems(share_token);

        if (items == null || items.isEmpty()) {
            model.addAttribute("error", "Ønskeseddel ikke fundet eller er tom");
            return "error";
        }

        model.addAttribute("items", items);
        model.addAttribute("shareToken", share_token); // fx til reservation

        return "wishlist-readonly";
    }

    @PostMapping("/wishlist/{id}/share")
    public String shareWishlist(@PathVariable("id") int wishlistId, RedirectAttributes redirectAttributes) {
        String token = wishListService.shareWishlist(wishlistId);
        redirectAttributes.addFlashAttribute("shareLink", "/view/" + token);
        return "redirect:/user/wishlist"; // eller hvor din oversigt er
    }

    @PostMapping("/reserve")
    public String reserveSharedItem(@RequestParam("itemId") long itemId,
                                    @RequestParam("shareToken") String shareToken,
                                    RedirectAttributes redirectAttributes) {
        try {
            wishListService.reserveSharedItem(itemId);
            redirectAttributes.addFlashAttribute("message", "Ønske er reserveret!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("message", "Ønsket er allerede reserveret.");
        }

        return "redirect:/view/" + shareToken;
    }

}
