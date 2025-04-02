package com.example.wishlist.Repository;

import com.example.wishlist.Model.SharedItem;
import com.example.wishlist.Rowmappers.SharedItemRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SharedItemRepository {

    private final JdbcTemplate jdbcTemplate;

    public SharedItemRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<SharedItem> findSharedItemsBySharedWishlistId(Long sharedWishlistId) {
        String sql = """
        SELECT si.*, r.reservation_id IS NOT NULL AS reserved
        FROM shared_items si
        LEFT JOIN tb_reservations r ON si.id = r.rsv_items_id
        WHERE si.shared_wishlist_id = ?
    """;
//        return jdbcTemplate.query(sql, new Object[]{sharedWishlistId}, new SharedItemRowMapper());
        return jdbcTemplate.query(sql, new SharedItemRowMapper(), sharedWishlistId);
    }

}
