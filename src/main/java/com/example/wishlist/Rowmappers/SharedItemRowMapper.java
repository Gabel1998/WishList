package com.example.wishlist.Rowmappers;

import com.example.wishlist.Model.SharedItem;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SharedItemRowMapper implements RowMapper<SharedItem> {
    @Override
    public SharedItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        SharedItem item = new SharedItem();
        item.setId(rs.getLong("id"));
        item.setOriginalItemId(rs.getLong("original_item_id"));
        item.setSharedWishlistId(rs.getLong("shared_wishlist_id"));
        item.setName(rs.getString("name"));
        item.setDescription(rs.getString("description"));
        item.setPrice(rs.getDouble("price"));
        item.setUrl(rs.getString("url"));
        item.setReserved(rs.getBoolean("reserved"));

        return item;
    }
}
