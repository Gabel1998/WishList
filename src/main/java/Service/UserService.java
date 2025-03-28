package Service;

import DTO.RegisterDTO;
import Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(RegisterDTO registerDTO) {
        userRepository.insertUser(registerDTO);
    }

    public boolean emailExists(String email) {
        return userRepository.emailExists(email);
    }
}


