package Repository;

import DTO.RegisterDTO;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    //Opretter jdbc-template for user
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Vores insert statement til mySql. Vi bruger ikke row mapper, da vi kun skal lave insert, og ikke mere
    public void registerUser(RegisterDTO registerDTO) {
        String sql = "INSERT INTO tb_user (name, email, password) values (?, ?, ?)"; // ? referer til de values vi får fra frontend, gennem RequestParam
        jdbcTemplate.update(sql, registerDTO.getName(), registerDTO.getEmail(), registerDTO.getPassword());
    }

    public void insertUser(RegisterDTO registerDTO) {
        String sql = "INSERT INTO tb_user ( email, password) values ( ?, ?)";
        jdbcTemplate.update(sql, registerDTO.getName(), registerDTO.getEmail(), registerDTO.getPassword());
    }

    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM tb_users WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }
}
