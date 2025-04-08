package com.example.wishlist.repository;

import com.example.wishlist.model.SharedItem;
import com.example.wishlist.rowmappers.SharedItemRowMapper;
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
        // Opdateret: Brug "si.id" i stedet for "si.shared_item_id"
        String sql = """
        SELECT si.*, r.reservation_id IS NOT NULL AS reserved
        FROM shared_items si
        LEFT JOIN tb_reservations r ON si.id = r.rsv_items_id
        WHERE si.shared_wishlist_id = ?
        """;
        return jdbcTemplate.query(sql, new SharedItemRowMapper(), sharedWishlistId);
    }
}
