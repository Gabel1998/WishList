package Repository;

import DTO.WishListDTO;
import Model.WishList;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class WishListRepository {

  private final JdbcTemplate jdbcTemplate;

  public WishListRepository(JdbcTemplate jdbcTemplate) {
      this.jdbcTemplate = jdbcTemplate;
  }


    public WishListDTO createWishList(){
        String sql ="INSERT INTO tb_wishlists(wishlist_id, )";

    }
}
