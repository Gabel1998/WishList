package Repository;

import DTO.ItemDTO;
import DTO.WishListDTO;
import Model.Item;
import Model.WishList;
import Rowmappers.ItemRowMapper;
import Rowmappers.WishListRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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


    public void deleteItem(int id) {
        String sql = "DELETE FROM tb_wishlists WHERE wishlist_id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void addItem(int id, ItemDTO itemDTO) {
        String sql = "INSERT INTO tb_wishlists_items(wishlist_id, item_id, name, quantity) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, id, itemDTO.getItemId(), itemDTO.getName(), itemDTO.getQuantity());
    }

    public void updateItem(int id, ItemDTO itemDTO) {
        String sql = "UPDATE tb_wishlists SET name = ? WHERE wishlist_id = ?";
        jdbcTemplate.update(sql, itemDTO.getName(), id);
    }

    public void reserveItem(int reservation_id, int rsv_items_id) {
        String sql = "UPDATE tb_wishlists_items SET reserved = TRUE, reservation_id = ? WHERE item_id = ?";
        jdbcTemplate.update(sql, reservation_id, rsv_items_id);
    }


}
