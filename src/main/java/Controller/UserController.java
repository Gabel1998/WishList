package Controller;

import DTO.RegisterDTO;
import Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

   private final UserService userService;

   public UserController(UserService userService){
       this.userService = userService;
   }

   //registrer Bruger (user) objekt
   @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterDTO registerDTO){


       //Kontrollere om email'en allerede er registreret i systemet
       if (userService.emailExists(registerDTO.getEmail())){
           return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists"); //Burde give fejlkode 409
       }
       //Her bliver brugere registreret
       userService.registerUser(registerDTO);
       return ResponseEntity.status(HttpStatus.CREATED).body("User registered"); // vi vil ikke returnere objektet her, vi nøjes bare med at sende en besked til frontend
   }

}
