package Service;

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
    public void registerUser(UserDto userDto) {
        String sql = "INSERT INTO users (name, email, password) values (?, ?, ?)"; // ? referer til de values vi får fra frontend, gennem RequestParam
        jdbcTemplate.update(sql, userDto.getName(), userDto.getEmail(), userDto.getPassword());
    }

}
