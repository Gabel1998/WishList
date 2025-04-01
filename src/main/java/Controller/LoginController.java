package Controller;

import Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
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
        }
    }

}
