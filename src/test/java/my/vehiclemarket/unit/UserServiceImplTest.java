package my.vehiclemarket.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import my.vehiclemarket.model.dto.UserRegisterDTO;
import my.vehiclemarket.model.dto.UserDetailsDTO;
import my.vehiclemarket.model.entity.UserEntity;
import my.vehiclemarket.model.entity.UserRoleEntity;
import my.vehiclemarket.repository.UserRepository;
import my.vehiclemarket.repository.UserRoleRepository;
import my.vehiclemarket.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    private UserServiceImpl toTest;

    @Captor
    private ArgumentCaptor<UserEntity> userEntityCaptor;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private UserRoleRepository mockUserRoleRepository;

    @Mock
    private PasswordEncoder mockPasswordEncoder;

    @BeforeEach
    void setUp() {
        toTest = new UserServiceImpl(
                mockUserRepository,
                new ModelMapper(),
                mockPasswordEncoder,
                mockUserRoleRepository
        );
        SecurityContextHolder.clearContext();
    }

    private void setUpSecurityContext(String username) {
        Authentication mockAuth = mock(Authentication.class);
        when(mockAuth.getName()).thenReturn(username);

        SecurityContext mockContext = mock(SecurityContext.class);
        when(mockContext.getAuthentication()).thenReturn(mockAuth);

        SecurityContextHolder.setContext(mockContext);
    }

    @Test
    void testGetCurrentUsername() {

        setUpSecurityContext("currentUsername");

        String username = toTest.getCurrentUsername();

        assertEquals("currentUsername", username);
    }

    @Test
    void testGetCurrentUser() {

        UserEntity user = new UserEntity();
        user.setUsername("currentUsername");
        setUpSecurityContext("currentUsername");

        when(mockUserRepository.findByUsername("currentUsername")).thenReturn(Optional.of(user));

        UserEntity currentUser = toTest.getCurrentUser();

        assertNotNull(currentUser);
        assertEquals("currentUsername", currentUser.getUsername());
    }

    @Test
    void testRegisterSuccess() {

        UserRegisterDTO dto = new UserRegisterDTO()
                .setUsername("newUser")
                .setPassword("password")
                .setConfirmPassword("password")
                .setEmail("email@example.com")
                .setName("User Name")
                .setPhone("1234567890");

        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setId(1); // Assuming role ID is 1

        when(mockUserRepository.findByUsername(dto.getUsername())).thenReturn(Optional.empty());
        when(mockPasswordEncoder.encode(dto.getPassword())).thenReturn("encodedPassword");
        when(mockUserRoleRepository.findById(1)).thenReturn(Optional.of(userRoleEntity));

        boolean result = toTest.register(dto);

        verify(mockUserRepository).save(userEntityCaptor.capture());
        UserEntity actualSavedEntity = userEntityCaptor.getValue();

        assertTrue(result);
        assertNotNull(actualSavedEntity);
        assertEquals("encodedPassword", actualSavedEntity.getPassword());
        assertTrue(actualSavedEntity.getRoles().contains(userRoleEntity));
    }

    @Test
    void testRegisterUserAlreadyExists() {

        UserRegisterDTO dto = new UserRegisterDTO()
                .setUsername("existingUser")
                .setPassword("password")
                .setConfirmPassword("password");

        when(mockUserRepository.findByUsername(dto.getUsername())).thenReturn(Optional.of(new UserEntity()));

        boolean result = toTest.register(dto);

        assertFalse(result);
        verify(mockUserRepository, times(0)).save(any(UserEntity.class));
    }

    @Test
    void testRegisterRoleNotFound() {

        UserRegisterDTO dto = new UserRegisterDTO()
                .setUsername("newUser")
                .setPassword("password")
                .setConfirmPassword("password");

        when(mockUserRepository.findByUsername(dto.getUsername())).thenReturn(Optional.empty());
        when(mockPasswordEncoder.encode(dto.getPassword())).thenReturn("encodedPassword");
        when(mockUserRoleRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> toTest.register(dto));
        assertEquals("Role not found", thrown.getMessage());
    }

    @Test
    void testFindUserByUsername() {

        UserEntity user = new UserEntity();
        user.setUsername("existingUser");

        when(mockUserRepository.findByUsername("existingUser")).thenReturn(Optional.of(user));

        UserEntity foundUser = toTest.findUserByUsername("existingUser");

        assertNotNull(foundUser);
        assertEquals("existingUser", foundUser.getUsername());
    }

    @Test
    void testFindUserByUsernameNotFound() {

        when(mockUserRepository.findByUsername("nonExistingUser")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> toTest.findUserByUsername("nonExistingUser"));
    }

    @Test
    void testDelete() {

        toTest.delete(1L);

        verify(mockUserRepository).deleteById(1L);
    }

    @Test
    void testGetAllUserDetails() {

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setName("User");
        user.setUsername("username");
        user.setEmail("email@example.com");
        user.setPhone("1234567890");

        when(mockUserRepository.findAll()).thenReturn(Collections.singletonList(user));

        List<UserDetailsDTO> userDetails = toTest.getAllUserDetails();

        assertNotNull(userDetails, "The returned user details list should not be null");
        assertEquals(1, userDetails.size(), "The list size should be 1");

        UserDetailsDTO details = userDetails.getFirst();
        assertEquals(1L, details.getId(), "User ID should be 1");
        assertEquals("User", details.getName(), "User name should match");
        assertEquals("username", details.getUsername(), "Username should match");
        assertEquals("email@example.com", details.getEmail(), "Email should match");
        assertEquals("1234567890", details.getPhone(), "Phone number should match");
    }
}
