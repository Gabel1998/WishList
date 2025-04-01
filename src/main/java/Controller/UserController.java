/// ==========================================
/// =      håndterer HTML-registrering       =
/// =   viser formular og viser feedback     =
/// ==========================================
package Controller;

import DTO.UserDTO;
import Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
/// MIDLERTIDIG SUPPRESSER TIL MVC ERROR PÅ LINJE 43 !!!

@RestController
@RequestMapping("/api")
public class UserController {

   private final UserService userService;

   public UserController(UserService userService){
       this.userService = userService;
   }

   //registrer Bruger (user) objekt
   @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO){


       //Kontrollere om email'en allerede er registreret i systemet
       if (userService.emailExists(userDTO.getEmail())){
           return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists"); //Burde give fejlkode 409
       }
       //Her bliver brugere registreret
       userService.registerUser(userDTO);
       return ResponseEntity.status(HttpStatus.CREATED).body("User registered"); // vi vil ikke returnere objektet her, vi nøjes bare med at sende en besked til frontend
   }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "register";
    }

    @PostMapping("/register")
    public String handleRegisterForm(@ModelAttribute("user") UserDTO userDTO, RedirectAttributes redirectAttributes) {

        //Kontrollere om email'en allerede er registreret i systemet
        if (userService.emailExists(userDTO.getEmail())) {

            // gemmer fejlbesked midlertidigt, som vises efter redirect <> flash attribute leveres kun én gang - perfekt til popups
            redirectAttributes.addFlashAttribute("errorMessage", "Email findes allerede.");
            return "redirect:/register";
        }
        /// Registrerer bruger i databasen
        userService.registerUser(userDTO);


        /// gemmer en success-besked til visning efter redirect
        redirectAttributes.addFlashAttribute("successMessage", "Bruger oprettet!");
        return "redirect:/register";
    }
}
