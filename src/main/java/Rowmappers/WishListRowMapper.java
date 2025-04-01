package Rowmappers;

import Model.User;
import Model.WishList;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WishListRowMapper implements RowMapper<WishList> {


    @Override
    public WishList mapRow(ResultSet rs, int rowNum) throws SQLException {
        WishList wishList = new WishList();

        wishList.setWishListId(rs.getInt("wishlist_id"));
        wishList.setName(rs.getString("title"));


        //map User
        User user = new User();
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        wishList.setUserId(user);

        return wishList;

    }
}
