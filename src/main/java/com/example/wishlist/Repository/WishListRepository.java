package com.example.wishlist.Repository;

import com.example.wishlist.DTO.WishListDTO;
import com.example.wishlist.Model.Item;
import com.example.wishlist.Model.WishList;
import com.example.wishlist.Rowmappers.ItemRowMapper;
import com.example.wishlist.Rowmappers.WishListRowMapper;
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
        String sql ="INSERT INTO tb_wishlists(wishlist_id, name) VALUES (?, ?)" ;
        jdbcTemplate.update(sql, wishListDTO.getWishListId(), wishListDTO.getName());
    }


    public void deleteItem(int itemId) {
        String sql = "DELETE FROM tb_items WHERE item_id = ?";
        jdbcTemplate.update(sql, itemId);
    }

    public WishList findWishListById(int wishlistId) {
        String sql = """
    SELECT w.*, u.email, u.password
    FROM tb_wishlists w
    JOIN tb_users u ON w.wl_user_id = u.user_id
    WHERE w.wishlist_id = ?
""";
        return jdbcTemplate.queryForObject(sql, new Object[]{wishlistId}, new WishListRowMapper());
    }

    public Item findItemById(int itemId) {
        String sql = "SELECT * FROM tb_items WHERE item_id = ?";
//        return jdbcTemplate.queryForObject(sql, new Object[]{itemId}, new ItemRowMapper());
        return jdbcTemplate.queryForObject(sql, new ItemRowMapper(), itemId);
    }

    public void insertItem(Item item) {
        String sql = "INSERT INTO tb_items (name, description, price, quantity, link) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                item.getName(), item.getDescription(),
                item.getPrice(), item.getQuantity(),
                item.getLink());
    }

    public void updateItem(Item item) {
        String sql = "UPDATE tb_items SET name = ?, description = ?, price = ?, quantity = ?, link = ? WHERE item_id = ?";
        jdbcTemplate.update(sql,
                item.getName(), item.getDescription(),
                item.getPrice(), item.getQuantity(),
                item.getLink(), item.getItemId());
    }

    public  WishList findByShareToken(String shareToken) {
        String sql = "SELECT * FROM tb_wishlists WHERE share_token = ?";
//        return jdbcTemplate.queryForObject(sql, new Object[]{shareToken}, new WishListRowMapper());
        return jdbcTemplate.queryForObject(sql, new WishListRowMapper(), shareToken);
    }

    public void insertSharedWishlist(long originalWishlistId) {
        String sql = "INSERT INTO shared_wishlists (original_wishlist_id, share_token) VALUES (?, UUID())";
        jdbcTemplate.update(sql, originalWishlistId);
    }

    public void copyItemsToSharedItems(long originalWishlistId, long sharedWishlistId) {
        String sql = """
        INSERT INTO shared_items (shared_wishlist_id, original_item_id, name, description, price, url)
        SELECT ?, item_id, name, description, price, url
        FROM tb_items
        WHERE it_wishlist_id = ?
    """;
        jdbcTemplate.update(sql, sharedWishlistId, originalWishlistId);
    }

    public String findShareTokenByOriginalWishlistId(long originalWishlistId) {
        String sql = "SELECT share_token FROM shared_wishlists WHERE original_wishlist_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, originalWishlistId);
    }

    public Long findSharedWishlistIdByToken(String shareToken) {
        String sql = "SELECT id FROM shared_wishlists WHERE share_token = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, shareToken);
    }

    public void insertReservation(long sharedItemId) {
        String sql = "INSERT INTO tb_reservations (rsv_items_id) VALUES (?)";
        jdbcTemplate.update(sql, sharedItemId);
    }

    // Tilføjet en metode for at hente ønskesedler ud fra bruger-id
    public List<WishList> findWishListsByUserId(int userId) {
        String sql = "SELECT * FROM tb_wishlists WHERE wl_user_id = ?";
        return jdbcTemplate.query(sql, new WishListRowMapper(), userId);
    }

    public List<Item> findItemsByWishListId(int wishlistId) {
        String sql = "SELECT * FROM tb_items WHERE it_wishlist_id = ?";
        return jdbcTemplate.query(sql, new ItemRowMapper(), wishlistId);
    }

    public List<WishList> findWishListsByUserEmail(String email) {
        String sql = """
                SELECT w.*, u.name, u.email, u.password
    FROM tb_wishlists w
    JOIN tb_users u ON w.wl_user_id = u.user_id
    WHERE u.email = ?
    """;
        return jdbcTemplate.query(sql, new WishListRowMapper(), email);
    }

    public int insertWishListAndReturnId(WishList wishList, String email) {
        String sql = """
        INSERT INTO tb_wishlists (name, wl_user_id)
        VALUES (?, (SELECT user_id FROM tb_users WHERE email = ?))
    """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, wishList.getName());
                ps.setString(2, email);
                return ps;
            }, keyHolder);

            if (keyHolder.getKey() == null) {
                throw new RuntimeException("Fejl: Oprettelse mislykkedes – intet ID returneret.");
            }

            return keyHolder.getKey().intValue();
        } catch (Exception e) {
            e.printStackTrace(); // ← vigtigt til debugging
            throw new RuntimeException("Kunne ikke oprette ønskeseddel", e);
        }
    }




}
