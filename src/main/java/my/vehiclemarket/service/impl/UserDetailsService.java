package my.vehiclemarket.service.impl;

import my.vehiclemarket.model.UserData;
import my.vehiclemarket.model.entity.UserEntity;
import my.vehiclemarket.model.entity.UserRoleEntity;
import my.vehiclemarket.model.enums.RolesEnum;
import my.vehiclemarket.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository
                .findByUsername(username)
                .map(UserDetailsService::map)
                .orElseThrow(() -> new UsernameNotFoundException("User with username" + username + " not found"));

    }

    private static UserDetails map(UserEntity userEntity) {
        return new UserData(
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getRoles().stream().map(UserRoleEntity::getRole).map(UserDetailsService::map).toList(),
                userEntity.getName()
        );
    }

    private static GrantedAuthority map(RolesEnum role) {
        return new SimpleGrantedAuthority("ROLE_" + role);
    }
}
