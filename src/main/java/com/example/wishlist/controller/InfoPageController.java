package com.example.wishlist.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InfoPageController {

    @GetMapping("/cookies")
    public String showCookiePolicy() {
        //noinspection SpringMVCViewInspection
        return "cookies"; // viser cookies.html
    }

    @GetMapping("/privacy")
    public String showPrivacyPolicy() {
        //noinspection SpringMVCViewInspection
        return "privacy"; // viser privacy.html
    }

    @GetMapping("/about")
    public String showAboutUsPage() {
        //noinspection SpringMVCViewInspection
        return "about-us"; // loader about-us.html fra /templates
    }

    @GetMapping("/shared")
    public String showSharedWishlists(HttpSession session) {
        String email = (String) session.getAttribute("user");

        if (email == null) {
            return "redirect:/login";
        }
      
        // Metode til at hente og vise delte ønskesedler
        //noinspection SpringMVCViewInspection
        return "shared-wishlists";
    }
}
