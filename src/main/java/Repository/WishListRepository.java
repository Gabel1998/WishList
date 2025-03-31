package Repository;

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

    public void deleteItem(int itemId) {
        String sql = "DELETE FROM tb_items WHERE item_id = ?";
        jdbcTemplate.update(sql, itemId);
    }

    public void insertWishList(WishList wishList) {
        String sql ="INSERT INTO tb_wishlists(wishlist_id, name, unique_url) VALUES (?, ?, ?)" ;
        jdbcTemplate.update(sql, wishList.getWishListId(), wishList.getName(), wishList.getUniqueURL());
    }
}
