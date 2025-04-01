package Controller;

import Service.UserService;
import DTO.LoginDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/api")
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
            return "redirect:/index";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Forkert email eller password.");
            return "redirect:/login";
        }
    }

    // Login logik (til API-request med JSON)
    @PostMapping("/api/login")
    @ResponseBody
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
