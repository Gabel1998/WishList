package Controller;

import DTO.UserDto;
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
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto){
       userService.registerUser(userDto);
       return ResponseEntity.status(HttpStatus.CREATED).body("User registered"); // vi vil ikke returnere objektet her, vi nøjes bare med at sende en besked til frontend
   }

}
