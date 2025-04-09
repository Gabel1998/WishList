package com.example.wishlist.service;


import com.example.wishlist.dto.LoginDTO;
import com.example.wishlist.dto.UserDTO;
import com.example.wishlist.model.User;
import com.example.wishlist.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // registrerer bruger med hashed password
    public void registerUser(UserDTO userDTO) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // opretter encoder
        String hashedPassword = passwordEncoder.encode(userDTO.getPassword()); // hasher password
        userDTO.setPassword(hashedPassword); // sætter hashed password i DTO
        userRepository.insertUser(userDTO);
    }

    public boolean emailExists(String email) {
        return userRepository.emailExists(email);
    }

    // tjek for korrekt email og password
    public boolean isValidUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if(user != null) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            return passwordEncoder.matches(password, user.getPassword()); // validerer password
        } else {
            return false; // email findes ikke
        }
    }

    public boolean login(LoginDTO loginDTO) {
        return isValidUser(loginDTO.getEmail(), loginDTO.getPassword());
    }
}


