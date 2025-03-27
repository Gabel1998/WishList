/// ============================================
/// =   JDBC-metoder til håndtering af items   =
/// ============================================
package Service;

import DTO.ItemDTO;
import org.springframework.jdbc.core.JdbcTemplate;

public class WishListService {
private JdbcTemplate jdbcTemplate;

    //tilføj produkt til databasen
    public void addItemToWishList(int wishlistId, ItemDTO item) {
        String sql = "INSERT INTO items (wishlist_id, name, description, price, quantity, link) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, wishlistId, item.getName(), item.getDescription(), item.getPrice(), item.getQuantity(), item.getLink());
    }

    //opdater produkt i databasen
    public void updateItem(int itemId, ItemDTO item) {
        String sql = "UPDATE items SET name = ?, description = ?, price = ?, quantity = ?, link = ? WHERE id = ?";
        jdbcTemplate.update(sql, item.getName(), item.getDescription(), item.getPrice(), item.getQuantity(), item.getLink(), itemId);
    }

    //slet produkt fra databasen
    public void deleteItem(int itemId) {
        String sql = "DELETE FROM items WHERE id = ?";
        jdbcTemplate.update(sql, itemId);
    }
}
