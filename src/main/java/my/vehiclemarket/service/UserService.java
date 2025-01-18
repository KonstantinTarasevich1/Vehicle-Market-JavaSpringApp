package my.vehiclemarket.service;

import my.vehiclemarket.model.dto.UserDetailsDTO;
import my.vehiclemarket.model.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserService {

    List<UserDetailsDTO> getAllUserDetails();
}
