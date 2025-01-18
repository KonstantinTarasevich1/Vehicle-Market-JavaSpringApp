package my.vehiclemarket.unit;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import my.vehiclemarket.model.dto.UserRegisterDTO;
import my.vehiclemarket.model.entity.UserEntity;
import my.vehiclemarket.model.entity.UserRoleEntity;
import my.vehiclemarket.repository.UserRepository;
import my.vehiclemarket.repository.UserRoleRepository;
import my.vehiclemarket.service.impl.AdminServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AdminServiceImplTest {

    private AdminServiceImpl adminService;

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
        adminService = new AdminServiceImpl(
                mockUserRepository,
                mockUserRoleRepository,
                mockPasswordEncoder,
                new ModelMapper()
        );
    }

    @Test
    void testRegisterAdminSuccess() {

        UserRegisterDTO userRegisterDTO = new UserRegisterDTO()
                .setUsername("admin")
                .setPassword("password")
                .setConfirmPassword("password")
                .setEmail("admin@example.com")
                .setName("Admin User")
                .setPhone("1234567890");

        UserEntity userEntity = new UserEntity();
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setId(2);

        when(mockUserRepository.findByUsername(userRegisterDTO.getUsername()))
                .thenReturn(Optional.empty());
        when(mockPasswordEncoder.encode(userRegisterDTO.getPassword()))
                .thenReturn("encodedpassword");
        when(mockUserRoleRepository.findById(2))
                .thenReturn(Optional.of(userRoleEntity));

        boolean result = adminService.registerAdmin(userRegisterDTO);

        verify(mockUserRepository, times(1)).save(userEntityCaptor.capture());
        UserEntity actualSavedEntity = userEntityCaptor.getValue();

        Assertions.assertTrue(result);
        Assertions.assertNotNull(actualSavedEntity);
        Assertions.assertEquals("encodedpassword", actualSavedEntity.getPassword());
        Assertions.assertEquals("admin", actualSavedEntity.getUsername());
        Assertions.assertEquals("admin@example.com", actualSavedEntity.getEmail());
        Assertions.assertEquals("Admin User", actualSavedEntity.getName());
        Assertions.assertEquals("1234567890", actualSavedEntity.getPhone());
        Assertions.assertTrue(actualSavedEntity.getRoles().contains(userRoleEntity));
    }

    @Test
    void testRegisterAdminWhenUserAlreadyExists() {

        UserRegisterDTO userRegisterDTO = new UserRegisterDTO()
                .setUsername("admin")
                .setPassword("password")
                .setConfirmPassword("password");

        UserEntity existingUser = new UserEntity();
        when(mockUserRepository.findByUsername(userRegisterDTO.getUsername()))
                .thenReturn(Optional.of(existingUser));

        boolean result = adminService.registerAdmin(userRegisterDTO);

        Assertions.assertFalse(result);
        verify(mockUserRepository, times(0)).save(any(UserEntity.class));
    }

    @Test
    void testRegisterAdminWhenRoleNotFound() {

        UserRegisterDTO userRegisterDTO = new UserRegisterDTO()
                .setUsername("admin")
                .setPassword("password")
                .setConfirmPassword("password");

        when(mockUserRepository.findByUsername(userRegisterDTO.getUsername()))
                .thenReturn(Optional.empty());
        when(mockPasswordEncoder.encode(userRegisterDTO.getPassword()))
                .thenReturn("encodedpassword");
        when(mockUserRoleRepository.findById(2))
                .thenReturn(Optional.empty());

        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> adminService.registerAdmin(userRegisterDTO));
        Assertions.assertEquals("Role not found", thrown.getMessage());
    }
}
