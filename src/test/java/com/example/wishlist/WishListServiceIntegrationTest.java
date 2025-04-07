package com.example.wishlist;

import com.example.wishlist.DTO.ItemDTO;
import com.example.wishlist.DTO.WishListDTO;
import com.example.wishlist.Model.SharedItem;
import com.example.wishlist.Model.WishList;
import com.example.wishlist.Service.WishListService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = "classpath:h2init.sql")
@Transactional
@Rollback(true)
public class WishListServiceIntegrationTest {

    @Autowired
    private WishListService service;

    @Test
    void testCreateAndGetWishList() {
        // Arrange
        WishList newList = new WishList();
        newList.setName("Testliste");
        int id = service.createWishListAndReturnId(newList, "test@example.com");

        // Act
        WishListDTO dto = service.getWishListById(id);

        // Assert
        assertNotNull(dto);
        assertEquals("Testliste", dto.getName());
    }

    @Test
    void testAddItemToWishList() {
        ItemDTO item = new ItemDTO();
        item.setName("Test gave");
        item.setDescription("Test beskrivelse");
        item.setPrice(Double.valueOf(99.99));
        item.setLink("http://gave.dk");

        service.addItemToWishList(1000, item); // 1000 er en eksisterende ønskeseddel

        WishListDTO dto = service.getWishListById(1000);
        assertEquals(2, dto.getItems().size()); // Én eksisterende + én ny
    }

    @Test
    void testUpdateItem() {
        ItemDTO update = new ItemDTO();
        update.setName("Opdateret Navn");
        update.setDescription("Opdateret beskrivelse");
        update.setPrice(Double.valueOf(123.45));
        update.setLink("http://nygave.dk");

        service.updateItem(10000, update);

        WishListDTO dto = service.getWishListById(1000);
        assertEquals("Opdateret Navn", dto.getItems().get(0).getName());
    }

    @Test
    void testDeleteItem() {
        service.deleteItem(10000); // eksisterende item
        WishListDTO dto = service.getWishListById(1000);
        assertTrue(dto.getItems().isEmpty());
    }

    @Test
    void testReserveItem() {
        service.reserveItem(123, 10000); // dummy reservationId
        WishListDTO dto = service.getWishListById(1000);
        ItemDTO reservedItem = dto.getItems().stream()
                .filter(it -> it.getItemId() == 10000)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Item not found"));

        assertTrue(reservedItem.isReserved());
    }

    @Test
    void testGetAllWishListsByUserId() {
        List<WishListDTO> lists = service.getAllWishListsByUser(100);
        assertFalse(lists.isEmpty());
    }

    @Test
    void testGetAllWishListsByUserEmail() {
        List<WishListDTO> lists = service.getAllWishListsByUserEmail("test@example.com");
        assertFalse(lists.isEmpty());
    }

    @Test
    void testShareWishlist() {
        String token = service.shareWishlist(1000);
        assertNotNull(token);
        WishList readOnly = service.findByShareToken(token);
        assertNotNull(readOnly);
    }

    @Test
    void testGetSharedItems() {
        String token = service.shareWishlist(1000);
        List<SharedItem> items = service.getSharedItems(token);
        assertFalse(items.isEmpty());
    }

    @Test
    void testReserveSharedItem() {
        service.reserveSharedItem(20000); // eksisterende delt item
        // Du kan evt. udvide SharedItemRepository med en læsemetode til at bekræfte det
    }
}
