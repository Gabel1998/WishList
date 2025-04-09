package com.example.wishlist;

import com.example.wishlist.Controller.UserController;
import com.example.wishlist.DTO.UserDTO;
import com.example.wishlist.Model.User;
import com.example.wishlist.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = "classpath:h2init.sql")  // H2 script til at oprette tabellen
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
@Rollback(true)
public class UserControllerIntegrationTest {

    @Autowired
    private UserController controller;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testShowRegisterForm_addsEmptyUserToModel() {
        Model model = new ExtendedModelMap();

        String viewName = controller.showRegisterForm(model);

        assertEquals("register", viewName);
        assertTrue(model.containsAttribute("user"));
        assertTrue(model.getAttribute("user") instanceof UserDTO);
    }

    @Test
    void testHandleRegisterForm_registersNewUser() {
        UserDTO newUser = new UserDTO("Integration Test", "integration@test.io", "Test1234");

        RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();
        MockHttpSession session = new MockHttpSession();

        String result = controller.handleRegisterForm(newUser, redirectAttributes, session);

        assertEquals("redirect:/register", result);
        assertEquals("Bruger oprettet!", redirectAttributes.getFlashAttributes().get("successMessage"));
        assertEquals("integration@test.io", session.getAttribute("user"));

        // Metode der finder bruger
        User user = userRepository.findByEmail("integration@test.io");
        assertNotNull(user);
        assertEquals("integration@test.io", user.getEmail());
    }

    @Test
    void testHandleRegisterForm_emailAlreadyExists() {
        UserDTO existingUser = new UserDTO("Eksisterende Bruger", "email@test.dk", "Kodeord");
        userRepository.insertUser(existingUser);


        UserDTO newUser = new UserDTO("Ny Bruger", "email@test.com", "Password");
        RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();
        MockHttpSession session = new MockHttpSession();

        String result = controller.handleRegisterForm(newUser, redirectAttributes, session);

        assertEquals("redirect:/register", result);
        assertEquals("Email findes allerede.", redirectAttributes.getFlashAttributes().get("errorMessage"));
    }
}
