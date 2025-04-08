package com.example.wishlist;

import com.example.wishlist.controller.UserController;
import com.example.wishlist.dto.UserDTO;
import com.example.wishlist.repository.UserRepository;
import com.example.wishlist.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Aktiverer Mockito til brug med JUnit 5
@ExtendWith(MockitoExtension.class)
public class UserControllerIntegrationTest {

    // til simulering af HTTP-anmodninger
    private MockMvc mockMvc;

    // til mock af UserService
    @Mock
    private UserService userService;

    // til mock af UserRepository
    @Mock
    private UserRepository userRepository;

    // til Injicering mock-objekter i controlleren
    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        // Initialiserer MockMvc med vores controller
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .build();
    }

    @Test
    public void testRegisterUser() throws Exception {
        // returnere false når emailExists kaldes
        when(userService.emailExists("testuser@tester.io")).thenReturn(false);
        // stubbe for testing
        doNothing().when(userService).registerUser(any(UserDTO.class));

        // Simulerer et POST-request til /register
        mockMvc.perform(post("/register")
                        .param("name", "Test User for Testing")
                        .param("email", "testuser@tester.io")
                        .param("password", "TestPassword@123"))
                // Forventer redirection som svar
                .andExpect(status().is3xxRedirection());

        // Bekræfter at registerUser blev kaldt [én gang]
        verify(userService, times(1)).registerUser(any(UserDTO.class));
    }

    @Test
    public void testRegisterUserWithExistingEmail() throws Exception {
        // returnere true - email findes allerede
        when(userService.emailExists("testuser@tester.io")).thenReturn(true);

        // registrering med eksisterende email
        mockMvc.perform(post("/register")
                        .param("name", "Test User for Testing")
                        .param("email", "testuser@tester.io")
                        .param("password", "TestPassword@123"))
                // vores controlleren bruger omdirigering, ikke 400 Bad Request
                .andExpect(status().is3xxRedirection());

        // Bekræft at registerUser aldrig blev kaldt fordi emailen allerede findes
        verify(userService, never()).registerUser(any(UserDTO.class));
    }

    @Test
    public void testRegisterUserWithInvalidEmailFormat() throws Exception {
        // stubbe for testing
        doNothing().when(userService).registerUser(any(UserDTO.class));

        // Tester registrering med ugyldig email-format
        mockMvc.perform(post("/register")
                        .param("name", "Test User for Testing")
                        .param("email", "invalid-email")
                        .param("password", "TestPassword@123"))
                .andExpect(status().is3xxRedirection());

        // controller kalder registerUser selv med ugyldig email
        verify(userService, times(1)).registerUser(any(UserDTO.class));
    }

    @Test
    public void testRegisterUserWithWeakPassword() throws Exception {
        // stubbe for testing
        doNothing().when(userService).registerUser(any(UserDTO.class));

        // registrering med for svagt kodeord
        mockMvc.perform(post("/register")
                        .param("name", "Test User for Testing")
                        .param("email", "weakpassword@tester.io")
                        .param("password", "123"))
                .andExpect(status().is3xxRedirection());

        // vores controller kalder registerUser selv med svagt kodeord
        verify(userService, times(1)).registerUser(any(UserDTO.class));
    }

    @Test
    public void testRegisterUserInDatabase() throws Exception {
        // returnere false når emailExists kaldes
        when(userService.emailExists("testdbuser@tester.io")).thenReturn(false);
        // stubbe for testing
        doNothing().when(userService).registerUser(any(UserDTO.class));

        // Simulerer registrering af en bruger i databasen
        mockMvc.perform(post("/register")
                        .param("name", "Test DB User")
                        .param("email", "testdbuser@tester.io")
                        .param("password", "SecurePass123"))
                .andExpect(status().is3xxRedirection());

        // Bekræft at registerUser blev kaldt [én gang]
        verify(userService, times(1)).registerUser(any(UserDTO.class));
    }
}