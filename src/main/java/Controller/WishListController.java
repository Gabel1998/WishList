/// ===========================================
/// =    Backend endpoints for ønskeliste     =
/// =     Tilføj, opdater og slet produkter   =
/// ===========================================
package Controller;

import DTO.ItemDTO;
import Service.WishListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public class WishListController {
private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
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

}
