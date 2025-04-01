package Service;

import DTO.LoginDTO;
import DTO.RegisterDTO;

import DTO.UserDTO;
import Repository.UserRepository;
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

    public boolean login(LoginDTO loginDTO) {
        return userRepository.emailExists(loginDTO.getEmail());
    }
}


