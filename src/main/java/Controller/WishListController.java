/// ===========================================
/// =    Backend endpoints for ønskeliste     =
/// =     Tilføj, opdater og slet produkter   =
/// ===========================================
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

    // tilføj nyt produkt til ønskeliste
    @PostMapping("/wishlist/{id}/item")
    public ResponseEntity<String> addItem(@PathVariable("id") int wishlistId, @RequestBody ItemDTO itemDTO) {
        wishListService.addItemToWishList(wishlistId, itemDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Item added to wishlist");
    }

    // opdater eksisterende produkt
    @PutMapping("/wishlist/item/{id}")
    public ResponseEntity<String> updateItem(@PathVariable("id") int itemId, @RequestBody ItemDTO itemDTO) {
        wishListService.updateItem(itemId, itemDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Item updated");
    }

    // slet produkt fra ønskeliste
    @DeleteMapping("/wishlist/item/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable("id") int itemId) {
        wishListService.deleteItem(itemId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Item deleted");
    }

    

    // reserver eksisterende produkt
    @PutMapping("/wishlist/item/{id}/reserve")
    public ResponseEntity<String> reserveItem(@PathVariable("id") int reservation_id, @RequestParam("item_id") int rsv_items_id) {
        wishListService.reserveItem(reservation_id, rsv_items_id);
        return ResponseEntity.status(HttpStatus.OK).body("Item reserved");
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
