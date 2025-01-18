package my.vehiclemarket.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import my.vehiclemarket.model.UserData;
import my.vehiclemarket.model.entity.UserEntity;
import my.vehiclemarket.model.entity.UserRoleEntity;
import my.vehiclemarket.model.enums.RolesEnum;
import my.vehiclemarket.repository.UserRepository;
import my.vehiclemarket.service.impl.UserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceTest {

    private static final String TEST_USERNAME = "user";
    private static final String NOT_EXISTENT_USERNAME = "noone";

    private UserDetailsService toTest;

    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp() {
        toTest = new UserDetailsService(mockUserRepository);
    }

    @Test
    void testLoadUserByUsername_UserFound() {

        UserEntity testUser = new UserEntity();
        testUser.setUsername(TEST_USERNAME);
        testUser.setPassword("topsecret");
        testUser.setName("John Doe");
        testUser.setRoles(List.of(
                new UserRoleEntity().setRole(RolesEnum.ADMIN),
                new UserRoleEntity().setRole(RolesEnum.USER)
        ));

        when(mockUserRepository.findByUsername(TEST_USERNAME))
                .thenReturn(Optional.of(testUser));

        UserDetails userDetails = toTest.loadUserByUsername(TEST_USERNAME);

        assertInstanceOf(UserData.class, userDetails);

        UserData userData = (UserData) userDetails;

        assertEquals(TEST_USERNAME, userData.getUsername());
        assertEquals(testUser.getPassword(), userData.getPassword());
        assertEquals(testUser.getName(), userData.getFullName());

        Set<GrantedAuthority> expectedAuthorities = Set.of(
                new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("ROLE_USER")
        );
        Set<GrantedAuthority> actualAuthorities = new HashSet<>(userData.getAuthorities());

        assertEquals(expectedAuthorities, actualAuthorities);
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {

        when(mockUserRepository.findByUsername(NOT_EXISTENT_USERNAME))
                .thenReturn(Optional.empty());

        assertThrows(
                UsernameNotFoundException.class,
                () -> toTest.loadUserByUsername(NOT_EXISTENT_USERNAME)
        );
    }
}
