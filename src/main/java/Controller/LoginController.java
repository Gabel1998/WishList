package Controller;

import Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import DTO.LoginDTO;
import Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/api")

public class LoginController {

    private final UserService userService;

    //konstruktør
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    //Login side
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }


    // Login Logik
    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {

        // tjekker om email og password er korrekt
        if (userService.isValidUser(email, password)) {
            session.setAttribute("user", email); // gemmer brugeren i sessionen
            return "redirect:/index"; // Redirect til index-siden efter login
        } else {
        redirectAttributes.addFlashAttribute("errorMessage", "Forkert email eller password.");
            return "redirect:/login"; // prøv igen

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
