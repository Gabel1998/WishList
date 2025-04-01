package Repository;

import DTO.ItemDTO;
import DTO.WishListDTO;
import Model.Item;
import Model.WishList;
import Rowmappers.ItemRowMapper;
import Rowmappers.WishListRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


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
        String sql = "SELECT * FROM wishlists WHERE wishlist_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{wishlistId}, new WishListRowMapper());
    }

    public Item findItemById(int itemId) {
        String sql = "SELECT * FROM tb_items WHERE item_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{itemId}, new ItemRowMapper());
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
        return jdbcTemplate.queryForObject(sql, new Object[]{shareToken}, new WishListRowMapper());
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


}
