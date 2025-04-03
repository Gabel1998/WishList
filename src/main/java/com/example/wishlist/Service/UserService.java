package com.example.wishlist.Service;


import com.example.wishlist.DTO.LoginDTO;
import com.example.wishlist.DTO.UserDTO;
import com.example.wishlist.Model.User;
import com.example.wishlist.Repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(UserDTO userDTO) {
        userRepository.insertUser(userDTO);
    }

    public boolean emailExists(String email) {
        return userRepository.emailExists(email);
    }

    // tjek for korrekt email og password
    public boolean isValidUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // opretter encoder
        return user != null && passwordEncoder.matches(password, user.getPassword()); //sammenligner hashed password

    }

    public boolean login(LoginDTO loginDTO) {
        return isValidUser(loginDTO.getEmail(), loginDTO.getPassword());
    }
}


