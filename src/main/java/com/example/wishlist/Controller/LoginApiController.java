package com.example.wishlist.Controller;

import com.example.wishlist.DTO.LoginDTO;
import com.example.wishlist.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginApiController {

    private final UserService userService;

    public LoginApiController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO, HttpSession session) {
        boolean isAuthenticated = userService.login(loginDTO);
        if (isAuthenticated) {
            session.setAttribute("userLogin", loginDTO.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }
}
