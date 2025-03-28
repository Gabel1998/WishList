package Service;

import DTO.RegisterDTO;
import DTO.UserDTO;
import DTO.UserDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final JdbcTemplate jdbcTemplate;

    //Opretter jdbc-template for user
    public UserService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Vores insert statement til mySql. Vi bruger ikke row mapper, da vi kun skal lave insert, og ikke mere
    public void registerUser(RegisterDTO registerDTO) {
        String sql = "INSERT INTO users (name, email, password) values (?, ?, ?)"; // ? referer til de values vi får fra frontend, gennem RequestParam
        jdbcTemplate.update(sql, registerDTO.getName(), registerDTO.getEmail(), registerDTO.getPassword());
    }

    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

}
