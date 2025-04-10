package com.example.wishlist.repository;

import com.example.wishlist.dto.UserDTO;
import com.example.wishlist.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    //Opretter jdbc-template for user
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Vores insert statement til mySql. Vi bruger ikke row mapper, da vi kun skal lave insert, og ikke mere
    public void registerUser(UserDTO userDTO) {
        String sql = "INSERT INTO tb_users (name, email, password) values (?, ?, ?)"; // ? referer til de values vi får fra frontend, gennem RequestParam
        jdbcTemplate.update(sql, userDTO.getName(), userDTO.getEmail(), userDTO.getPassword());
    }

    public void insertUser(UserDTO userDTO) {
        String sql = "INSERT INTO tb_users (name, email, password) values ( ?, ?, ?)";
        jdbcTemplate.update(sql, userDTO.getName(), userDTO.getEmail(), userDTO.getPassword());
    }

    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM tb_users WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    //Tjekker om email eksisterer i databasen
    public User findByEmail(String email) {
        String sql = "SELECT * FROM tb_users WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            User user = new User();
            user.setEmail(rs.getString("email"));
            return user;
        }, email);
    }
}
