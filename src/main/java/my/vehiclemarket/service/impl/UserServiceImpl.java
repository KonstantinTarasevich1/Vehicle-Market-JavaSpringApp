package my.vehiclemarket.service.impl;

import my.vehiclemarket.model.dto.UserDetailsDTO;
import my.vehiclemarket.model.dto.UserRegisterDTO;
import my.vehiclemarket.model.entity.UserEntity;
import my.vehiclemarket.model.entity.UserRoleEntity;
import my.vehiclemarket.repository.UserRepository;
import my.vehiclemarket.repository.UserRoleRepository;
import my.vehiclemarket.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
    }

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
    public UserEntity getCurrentUser() {
        String username = getCurrentUsername();
        return findUserByUsername(username);
    }

    public boolean register(UserRegisterDTO data) {
        Optional<UserEntity> existingUser = userRepository.findByUsername(data.getUsername());

        if (existingUser.isPresent()) {
            return false;
        }

        UserEntity user = modelMapper.map(data, UserEntity.class);


        user.setPassword(passwordEncoder.encode(data.getPassword()));

        UserRoleEntity userRole = userRoleRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.getRoles().add(userRole);

        this.userRepository.save(user);

        return true;
    }

    public UserEntity findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found"));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserDetailsDTO> getAllUserDetails() {
        return userRepository
                .findAll()
                .stream()
                .map(UserServiceImpl::toUserDetails)
                .toList();
    }

    private static UserDetailsDTO toUserDetails(UserEntity userEntity) {
        return new UserDetailsDTO(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getPhone()
        );

    }

}
