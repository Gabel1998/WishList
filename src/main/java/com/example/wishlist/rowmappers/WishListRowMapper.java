package com.example.wishlist.rowmappers;

import com.example.wishlist.model.User;
import com.example.wishlist.model.WishList;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WishListRowMapper implements RowMapper<WishList> {
    @Override
    public WishList mapRow(ResultSet rs, int rowNum) throws SQLException {
        WishList wishList = new WishList();
        wishList.setWishListId(rs.getInt("wishlist_id"));
        // Kortlægger ønskeseddelens navn fra kolonnen "title"
        wishList.setName(rs.getString("name"));

        User user = new User();
        // Henter brugerens navn fra aliaset "user_name"
        user.setName(rs.getString("user_name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));

        wishList.setUser(user);
        return wishList;
    }
}
