package com.example.wishlist.Rowmappers;

import com.example.wishlist.Model.User;
import com.example.wishlist.Model.WishList;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WishListRowMapper implements RowMapper<WishList> {
    @Override
    public WishList mapRow(ResultSet rs, int rowNum) throws SQLException {
        WishList wishList = new WishList();
        wishList.setWishListId(rs.getInt("wishlist_id"));
        wishList.setName(rs.getString("title")); // ← opdateret fra name til title

        User user = new User();
        user.setName(rs.getString("name")); // hentes fra JOIN på tb_users
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));

        wishList.setUser(user);
        return wishList;
    }
}
