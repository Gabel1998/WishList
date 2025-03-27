/// ==========================================
/// =      håndterer HTML-registrering       =
/// =   viser formular og viser feedback     =
/// ==========================================
package Controller;

import DTO.UserDto;
import Service.UserService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String handleRegisterForm(@ModelAttribute("user") UserDto userDto,
                                     RedirectAttributes redirectAttributes) {

        //Kontrollere om email'en allerede er registreret i systemet
        if (userService.emailExists(userDto.getEmail())) {

            // gemmer fejlbesked midlertidigt, som vises efter redirect <> flash attribute leveres kun én gang - perfekt til popups
            redirectAttributes.addFlashAttribute("errorMessage", "Email findes allerede.");
            return "redirect:/register";
        }
        /// Registrerer bruger i databasen
        userService.registerUser(userDto);

        /// gemmer en success-besked til visning efter redirect
        redirectAttributes.addFlashAttribute("successMessage", "Bruger oprettet!");
        return "redirect:/register";
    }

}
