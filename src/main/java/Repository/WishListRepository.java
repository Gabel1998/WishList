package Repository;

import DTO.WishListDTO;
import org.springframework.jdbc.core.JdbcTemplate;


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
}
