package com.example.wishlist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InfoPageController {

    @GetMapping("/cookies")
    public String showCookiePolicy() {
        return "cookies"; // viser cookies.html
    }

    @GetMapping("/privacy")
    public String showPrivacyPolicy() {
        return "privacy"; // viser privacy.html
    }

    @GetMapping("/about")
    public String showAboutUsPage() {
        return "about-us"; // loader about-us.html fra /templates
    }

}
