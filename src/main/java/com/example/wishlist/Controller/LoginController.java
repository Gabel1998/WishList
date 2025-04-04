package com.example.wishlist.Controller;

import com.example.wishlist.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    private final UserService userService;

    // konstruktør
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    // Login side (til Thymeleaf-view)
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    // Login logik (til Thymeleaf-form)
    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {

        if (userService.isValidUser(email, password)) {
            session.setAttribute("user", email);
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Forkert email eller password.");
            return "redirect:/login";
        }
    }

}
