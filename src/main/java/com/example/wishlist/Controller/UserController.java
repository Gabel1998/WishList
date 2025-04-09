/// ==========================================
/// =      håndterer HTML-registrering       =
/// =   viser formular og viser feedback     =
/// ==========================================
package com.example.wishlist.Controller;

import com.example.wishlist.DTO.UserDTO;
import com.example.wishlist.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

   private final UserService userService;

   public UserController(UserService userService){
       this.userService = userService;
   }


    @GetMapping("/register")
    public String showRegisterForm(Model model) {
       if (!model.containsAttribute("user")) {
           model.addAttribute("user", new UserDTO("name", "email", "password"));
       }
        return "register";
    }

    @PostMapping("/register")
    public String handleRegisterForm(@ModelAttribute("user") UserDTO userDTO, RedirectAttributes redirectAttributes, HttpSession session) {

        //Kontrollere om email'en allerede er registreret i systemet
        if (userService.emailExists(userDTO.getEmail())) {

            // gemmer fejlbesked midlertidigt, som vises efter redirect <> flash attribute leveres kun én gang - perfekt til popups
            redirectAttributes.addFlashAttribute("errorMessage", "Email findes allerede.");
            return "redirect:/register";
        }
        /// Registrerer bruger i databasen
        userService.registerUser(userDTO);

        /// auto-login bruger efter registrering
        session.setAttribute("user", userDTO.getEmail());

        /// Gemmer en success-besked til visning efter redirect
        redirectAttributes.addFlashAttribute("successMessage", "Bruger oprettet!");
        return "redirect:/register";
    }

    /// til at bruger kan slette sin konto? eller måske bare have "kontakt support"?
//    @GetMapping("/profile")
//    public String showUserProfile(HttpSession session, Model model) {
//        String email = (String) session.getAttribute("user");
//        if (email == null) {
//            return "redirect:/login";
//        }
//
//        UserDTO user = userService.getUserByEmail(email);
//        model.addAttribute("user", user);
//        return "profile";
//    }

}
