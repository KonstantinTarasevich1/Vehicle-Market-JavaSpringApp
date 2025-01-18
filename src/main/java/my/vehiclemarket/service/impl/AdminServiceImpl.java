package my.vehiclemarket.service.impl;

import my.vehiclemarket.model.dto.UserRegisterDTO;
import my.vehiclemarket.model.entity.UserEntity;
import my.vehiclemarket.model.entity.UserRoleEntity;
import my.vehiclemarket.repository.UserRepository;
import my.vehiclemarket.repository.UserRoleRepository;
import my.vehiclemarket.service.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public AdminServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public boolean registerAdmin(UserRegisterDTO data) {
        Optional<UserEntity> existingUser = userRepository.findByUsername(data.getUsername());

        if (existingUser.isPresent()) {
            return false;
        }

        UserEntity user = modelMapper.map(data, UserEntity.class);


        user.setPassword(passwordEncoder.encode(data.getPassword()));

        UserRoleEntity userRole = userRoleRepository.findById(2)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.getRoles().add(userRole);

        this.userRepository.save(user);

        return true;
    }
}
