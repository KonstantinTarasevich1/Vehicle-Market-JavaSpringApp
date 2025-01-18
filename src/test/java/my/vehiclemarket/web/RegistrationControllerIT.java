package my.vehiclemarket.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import my.vehiclemarket.model.dto.UserRegisterDTO;
import my.vehiclemarket.model.entity.UserEntity;
import my.vehiclemarket.repository.UserRepository;
import my.vehiclemarket.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class RegistrationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void testShowRegisterForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/register"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("register"));
    }

    @Test
    void testSuccessfulRegistration() throws Exception {
        mockMvc.perform(post("/users/register")
                        .param("name", "Jane sdfoe")
                        .param("email", "ja.sdaaafdoe@example.com")
                        .param("phone", "0987654321")
                        .param("username", "jsaaaadfedoe")
                        .param("password", "password123")
                        .param("confirmPassword", "password123")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/login"));

        Optional<UserEntity> userEntityOpt = userRepository.findByUsername("jsaaaadfedoe");

        Assertions.assertTrue(userEntityOpt.isPresent());

        UserEntity userEntity = userEntityOpt.get();

        Assertions.assertEquals("Jane sdfoe", userEntity.getName());
        Assertions.assertEquals("ja.sdaaafdoe@example.com", userEntity.getEmail());
        Assertions.assertEquals("0987654321", userEntity.getPhone());
        Assertions.assertTrue(passwordEncoder.matches("password123", userEntity.getPassword()));
    }

    @Test
    void testRegistrationWithExistingUsername() throws Exception {

        UserRegisterDTO existingUserDTO = new UserRegisterDTO();
        existingUserDTO.setName("John Doe");
        existingUserDTO.setEmail("john.doe@example.com");
        existingUserDTO.setPhone("1234567890");
        existingUserDTO.setUsername("existinguser");
        existingUserDTO.setPassword("password123");
        existingUserDTO.setConfirmPassword("password123");

        userService.register(existingUserDTO);

        mockMvc.perform(post("/users/register")
                        .param("name", "Jane Smith")
                        .param("email", "jane.smith@example.com")
                        .param("phone", "0987654321")
                        .param("username", "existinguser")
                        .param("password", "password123")
                        .param("confirmPassword", "password123")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/register"))
                .andExpect(result -> {
                    Assertions.assertNotNull(result.getFlashMap().get("usernameExistsError"));
                });
    }


    @Test
    void testRegistrationWithPasswordMismatch() throws Exception {
        mockMvc.perform(post("/users/register")
                        .param("name", "Alice")
                        .param("email", "alice@example.com")
                        .param("phone", "123456789")
                        .param("username", "aliceuser")
                        .param("password", "password123")
                        .param("confirmPassword", "password1234") // Mismatch
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/register"))
                .andExpect(result -> {
                    Assertions.assertNotNull(result.getFlashMap().get("passwordMismatchError"));
                });
    }


}
