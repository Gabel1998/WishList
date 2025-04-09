/// ==========================================
/// =      håndterer HTML-registrering       =
/// =   viser formular og viser feedback     =
/// ==========================================
package com.example.wishlist.controller;

import com.example.wishlist.dto.UserDTO;
import com.example.wishlist.service.UserService;
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
        //noinspection SpringMVCViewInspection
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
        /// Registrerer bruger i datab
        userService.registerUser(userDTO);

        /// Auto-login bruger efter registrering
        session.setAttribute("user", userDTO.getEmail());

        /// Gemmer en success-besked til visning efter redirect
        redirectAttributes.addFlashAttribute("successMessage", "Bruger oprettet!");
        return "redirect:/";
    }

}
