package com.example.wishlist;

import com.example.wishlist.dto.ItemDTO;
import com.example.wishlist.dto.WishListDTO;
import com.example.wishlist.model.SharedItem;
import com.example.wishlist.model.WishList;
import com.example.wishlist.service.WishListService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = "classpath:h2init.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
@Rollback(true)
public class WishListServiceIntegrationTest {

    @Autowired
    private WishListService service;

    @Test
    void testCreateAndGetWishList() {
        WishList newList = new WishList();
        // Her skal newList.setName() mappes til kolonnen 'title' i databasen.
        newList.setName("Testliste");
        int id = service.createWishListAndReturnId(newList, "test@example.com");

        WishListDTO dto = service.getWishListById(id);

        assertNotNull(dto);
        assertEquals("Testliste", dto.getName());
    }

    @Test
    void testAddItemToWishList() {
        ItemDTO item = new ItemDTO();
        item.setName("Test gave");
        item.setDescription("Test beskrivelse");
        item.setPrice(99.99);
        // Sørg for at din mapper omdøber 'link' til 'url' ved indsættelse.
        item.setUrl("http://gave.dk");

        service.addItemToWishList(1000, item);
        WishListDTO dto = service.getWishListById(1000);
        // Forvent, at der nu er 2 items: det eksisterende item og det nye.
        assertEquals(2, dto.getItems().size());
    }

    @Test
    void testUpdateItem() {
        ItemDTO update = new ItemDTO();
        update.setName("Opdateret Navn");
        update.setDescription("Opdateret beskrivelse");
        update.setPrice(123.45);
        update.setUrl("http://nygave.dk");

        service.updateItem(10000, update);
        WishListDTO dto = service.getWishListById(1000);
        assertEquals("Opdateret Navn", dto.getItems().get(0).getName());
    }

    @Test
    void testDeleteItem() {
        service.deleteItem(10000);
        WishListDTO dto = service.getWishListById(1000);
        assertTrue(dto.getItems().isEmpty());
    }

    @Test
    void testReserveItem() {
        service.reserveItem(123, 10000);
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
        service.reserveSharedItem(20000);
        // Her kan du udvide testen med en verificering af, at reservationen er blevet gennemført.
    }
}
