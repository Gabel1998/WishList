package com.example.wishlist.repository;

import com.example.wishlist.dto.WishListDTO;
import com.example.wishlist.model.Item;
import com.example.wishlist.model.WishList;
import com.example.wishlist.rowmappers.ItemRowMapper;
import com.example.wishlist.rowmappers.WishListRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class WishListRepository {

    private final JdbcTemplate jdbcTemplate;

    public WishListRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertWishList(WishListDTO wishListDTO) {
        // Ændret: indsæt i "title" i stedet for "name"
        String sql = "INSERT INTO tb_wishlists(wishlist_id, title) VALUES (?, ?)";
        jdbcTemplate.update(sql, wishListDTO.getWishListId(), wishListDTO.getName());
    }

    public void deleteItem(int itemId) {
        String sql = "DELETE FROM tb_items WHERE item_id = ?";
        jdbcTemplate.update(sql, itemId);
    }

    public WishList findWishListById(int wishlistId) {
        String sql = """
        SELECT w.wishlist_id, w.wl_user_id, w.title, w.share_token, u.email, u.password, u.name AS user_name
        FROM tb_wishlists w
        JOIN tb_users u ON w.wl_user_id = u.user_id
        WHERE w.wishlist_id = ?
    """;
        return jdbcTemplate.queryForObject(sql, new Object[]{wishlistId}, new WishListRowMapper());
    }

    public Item findItemById(int itemId) {
        // 1. Hent selve item
        String itemSql = "SELECT * FROM tb_items WHERE item_id = ?";
        Item item = jdbcTemplate.queryForObject(itemSql, new ItemRowMapper(), itemId);

        if (item != null) {
            // 2. Hent ønskeseddel (inkl. brugerinfo, som din WishListRowMapper kræver)
            String wishlistSql = """
            SELECT w.wishlist_id, w.wl_user_id, w.title, w.share_token,
                   u.email, u.password, u.name AS user_name
            FROM tb_wishlists w
            JOIN tb_users u ON w.wl_user_id = u.user_id
            WHERE w.wishlist_id = (SELECT it_wishlist_id FROM tb_items WHERE item_id = ?)
        """;
            WishList wishList = jdbcTemplate.queryForObject(wishlistSql, new WishListRowMapper(), itemId);

            // 3. Sæt relationen
            item.setWishList(wishList);
        }

        return item;
    }


    public void insertItem(Item item) {
        String sql = """
            INSERT INTO tb_items (name, description, price, url, it_wishlist_id)
            VALUES (?, ?, ?, ?, ?)
        """;
        jdbcTemplate.update(sql,
                item.getName(),
                item.getDescription(),
                item.getPrice(),
                item.getUrl(),
                item.getWishlistId().getWishListId());
    }

    public void updateItem(Item item) {
        String sql = """
            UPDATE tb_items
            SET name = ?, description = ?, price = ?, url = ?, reserved = ?
            WHERE item_id = ?
        """;
        jdbcTemplate.update(sql,
                item.getName(),
                item.getDescription(),
                item.getPrice(),
                item.getUrl(),
                item.getReserved(),
                item.getItemId());
    }

    public WishList findByShareToken(String shareToken) {
        String sql = """
            SELECT w.wishlist_id, w.wl_user_id, w.title, w.share_token, u.email, u.password, u.name AS user_name
            FROM tb_wishlists w
            JOIN shared_wishlists s ON s.original_wishlist_id = w.wishlist_id
            JOIN tb_users u ON w.wl_user_id = u.user_id
            WHERE s.share_token = ?
        """;
        return jdbcTemplate.queryForObject(sql, new WishListRowMapper(), shareToken);
    }

    public void insertSharedWishlist(long originalWishlistId) {
        String sql = """
            INSERT INTO shared_wishlists (original_wishlist_id, share_token)
            VALUES (?, UUID())
        """;
        jdbcTemplate.update(sql, originalWishlistId);
    }

    public void copyItemsToSharedItems(long originalWishlistId, long sharedWishlistId) {
        String sql = """
            INSERT INTO shared_items (
                shared_wishlist_id, original_item_id, name, description, price, url)
            SELECT ?, item_id, name, description, price, url
            FROM tb_items
            WHERE it_wishlist_id = ?
        """;
        jdbcTemplate.update(sql, sharedWishlistId, originalWishlistId);
    }

    public String findShareTokenByOriginalWishlistId(long originalWishlistId) {
        String sql = """
            SELECT share_token
            FROM shared_wishlists
            WHERE original_wishlist_id = ?
            ORDER BY created_at DESC
            LIMIT 1
        """;
        return jdbcTemplate.queryForObject(sql, String.class, originalWishlistId);
    }

    // Ændret: brug "id" i stedet for "shared_wishlist_id"
    public Long findSharedWishlistIdByToken(String shareToken) {
        String sql = "SELECT id FROM shared_wishlists WHERE share_token = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, shareToken);
    }

    public void insertReservation(long sharedItemId) {
        String sql = "INSERT INTO tb_reservations (rsv_items_id) VALUES (?)";
        jdbcTemplate.update(sql, sharedItemId);
    }

    public List<WishList> findWishListsByUserId(int userId) {
        String sql = """
            SELECT w.wishlist_id, w.wl_user_id, w.title, w.share_token, u.email, u.password, u.name AS user_name
            FROM tb_wishlists w
            JOIN tb_users u ON w.wl_user_id = u.user_id
            WHERE w.wl_user_id = ?
        """;
        return jdbcTemplate.query(sql, new WishListRowMapper(), userId);
    }

    public List<Item> findItemsByWishListId(int wishlistId) {
        String sql = "SELECT * FROM tb_items WHERE it_wishlist_id = ?";
        return jdbcTemplate.query(sql, new ItemRowMapper(), wishlistId);
    }

    public List<WishList> findWishListsByUserEmail(String email) {
        String sql = """
        SELECT w.wishlist_id, w.wl_user_id, w.title, w.share_token, u.email, u.password, u.name AS user_name
        FROM tb_wishlists w
        JOIN tb_users u ON w.wl_user_id = u.user_id
        WHERE u.email = ?
    """;
        return jdbcTemplate.query(sql, new WishListRowMapper(), email);
    }

    // Opdateret metode til at indsætte en ønskeseddel og returnere dens ID – indsætter i "title" i stedet for "name"
    public int insertWishListAndReturnId(WishList wishList, String email) {
        String sql = """
        INSERT INTO tb_wishlists (title, wl_user_id)
        VALUES (?, (SELECT user_id FROM tb_users WHERE email = ?))
    """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, wishList.getName()); // wishList.getName() indeholder det ønskede navn, som nu svarer til kolonnen "title"
                ps.setString(2, email);
                return ps;
            }, keyHolder);

            if (keyHolder.getKey() == null) {
                throw new RuntimeException("Fejl: Oprettelse mislykkedes – intet ID returneret.");
            }
            return keyHolder.getKey().intValue();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Kunne ikke oprette ønskeseddel", e);
        }
    }

    public String insertSharedWishlistAndReturnToken(long originalWishlistId) {
        String sql = """
        INSERT INTO shared_wishlists (original_wishlist_id, share_token)
        VALUES (?, UUID())
    """;
        jdbcTemplate.update(sql, originalWishlistId);
        return findLatestShareTokenForWishlist((int) originalWishlistId);
    }

    public String findLatestShareTokenForWishlist(int wishlistId) {
        String sql = """
        SELECT share_token
        FROM shared_wishlists
        WHERE original_wishlist_id = ?
        ORDER BY created_at DESC
        LIMIT 1
    """;
        List<String> result = jdbcTemplate.queryForList(sql, String.class, wishlistId);
        return result.isEmpty() ? null : result.get(0);
    }


    public void deleteWishlist(int wishlistId) {
        String sql = "DELETE FROM tb_wishlists WHERE wishlist_id = ?";
        jdbcTemplate.update(sql, wishlistId);
    }
}
