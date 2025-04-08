package com.example.wishlist.rowmappers;

import com.example.wishlist.model.SharedItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SharedItemRowMapper implements RowMapper<SharedItem> {
    @Override
    public SharedItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        SharedItem item = new SharedItem();
        item.setSharedWishlistId(rs.getInt("shared_wishlist_id"));
        item.setOriginalItemId(rs.getInt("original_item_id"));
        item.setName(rs.getString("name"));
        item.setDescription(rs.getString("description"));
        item.setPrice(rs.getDouble("price"));
        item.setUrl(rs.getString("url"));
        return item;
    }
}
