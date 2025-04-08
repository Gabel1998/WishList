package com.example.wishlist.controller;

import com.example.wishlist.dto.ItemDTO;
import com.example.wishlist.dto.WishListDTO;
import com.example.wishlist.model.SharedItem;
import com.example.wishlist.model.WishList;
import com.example.wishlist.repository.WishListRepository;
import com.example.wishlist.service.WishListService;
import jakarta.servlet.http.HttpSession;
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
    public WishListController(WishListService wishListService, WishListRepository wishListRepository) {
        this.wishListService = wishListService;
    }

    // Forside
    @GetMapping
    public String index() {
        return "index";
    }

     //Oversigt over ønskesedler
    @GetMapping("/wishlists")
    public String showWishListOverview(Model model, HttpSession session) {
        String email = (String) session.getAttribute("user");

        if (email == null) {
            return "redirect:/login";
        }

        List<WishListDTO> wishLists = wishListService.getAllWishListsByUserEmail(email);
        model.addAttribute("wishlists", wishLists);
        return "wishlist-overview";
    }

    // Formular til at oprette ønskeseddel
    @GetMapping("/Wishlist-form")
    public String showWishListForm(Model model) {
        model.addAttribute("wishlist", new WishList());
        return "Wishlist-form";
    }

    // Behandler oprettelse og redirecter til /wishlist/{id}
    @PostMapping("/Wishlist-form")
    public String handleWishListForm(@ModelAttribute WishList wishList, HttpSession session) {
        String email = (String) session.getAttribute("user");

        if (email == null) {
            return "redirect:/login";
        }

        int newId = wishListService.createWishListAndReturnId(wishList, email);
        System.out.println("Ny ønskeseddel oprettet med ID: " + newId); // debugging
        return "redirect:/wishlist/" + newId;
    }

    // Vis én ønskeseddel med alle tilknyttede produkter
    @GetMapping("/wishlist/{id}")
    public String showSingleWishlist(@PathVariable("id") int wishlistId, Model model)
    {
        WishListDTO wishlist = wishListService.getWishListById(wishlistId);
        if (wishlist == null) {
            //noinspection SpringMVCViewInspection
            return "error";
        }

        String shareToken = wishListService.getLatestShareTokenForWishlist(wishlistId);
        if (shareToken != null) {
            wishlist.setShareToken(shareToken);
        }

        model.addAttribute("wishlist", wishlist);
        return "wishlist";
    }



    // Tilføj produkt
    @PostMapping("/wishlist/{id}/item")
    public String addItem(@PathVariable("id") int wishlistId,
                          @ModelAttribute ItemDTO itemDTO,
                          RedirectAttributes redirectAttributes) {
        wishListService.addItemToWishList(wishlistId, itemDTO);
        redirectAttributes.addFlashAttribute("message", "Produkt tilføjet!");
        return "redirect:/wishlist/" + wishlistId;
    }




    @PostMapping("/wishlist/item/{id}")
    public String updateItem(@PathVariable("id") Long itemId,
                             @ModelAttribute("item") ItemDTO itemDTO) {

        wishListService.updateItem(Math.toIntExact(itemId), itemDTO);
        return "redirect:/wishlist/" + itemDTO.getWishlistId(); // eller hvor du vil redirecte til
    }


    @DeleteMapping("/wishlist/item/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable("id") int itemId) {
        wishListService.deleteItem(itemId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Ønske slettet");
    }

    @PutMapping("/wishlist/item/{id}/reserve")
    public ResponseEntity<String> reserveItem(@PathVariable("id") int reservationId, @RequestParam("item_id") int rsvItemsId) {
        wishListService.reserveItem(reservationId, rsvItemsId);
        return ResponseEntity.status(HttpStatus.OK).body("Ønske reserveret");
    }

    // Vis readonly ønskeseddel via token
    @GetMapping("/view/{share_token}")
    public String viewReadOnly(@PathVariable String share_token, Model model) {
        List<SharedItem> items = wishListService.getSharedItems(share_token);

        if (items == null || items.isEmpty()) {
            model.addAttribute("error", "Ønskeseddel ikke fundet eller er tom");
            //noinspection SpringMVCViewInspection
            return "error";
        }

        model.addAttribute("items", items);
        model.addAttribute("shareToken", share_token);

        // 🛠 Tilføj denne linje:
        WishList wishlist = wishListService.findByShareToken(share_token);
        model.addAttribute("wishlist", wishlist);

        //noinspection SpringMVCViewInspection
        return "wishlist-readonly";
    }


    // Share ønskeseddel
    @PostMapping("/wishlist/{id}/share")
    public String shareWishlist(@PathVariable("id") int wishlistId) {
        wishListService.shareWishlist(wishlistId);
        return "redirect:/wishlist/" + wishlistId;
    }


    // Reserve via readonly-view
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

    @GetMapping("/wishlist/item/{id}/edit")
    public String showEditForm(@PathVariable("id") int itemId, Model model) {
        ItemDTO item = wishListService.getItemById(itemId); // Du skal have denne metode i service
        model.addAttribute("item", item);
        //noinspection SpringMVCViewInspection
        return "edit-item";
    }

}
