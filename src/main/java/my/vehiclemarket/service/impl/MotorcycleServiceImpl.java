package my.vehiclemarket.service.impl;

import my.vehiclemarket.model.dto.*;
import my.vehiclemarket.model.entity.MotorcycleEntity;
import my.vehiclemarket.model.entity.UserEntity;
import my.vehiclemarket.repository.MotorcycleRepository;
import my.vehiclemarket.service.MotorcycleService;
import my.vehiclemarket.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MotorcycleServiceImpl implements MotorcycleService {

    private final MotorcycleRepository motorcycleRepository;
    private final ModelMapper modelMapper;
    private final UserServiceImpl userService;


    public MotorcycleServiceImpl(MotorcycleRepository motorcycleRepository, ModelMapper modelMapper, UserServiceImpl userService) {
        this.motorcycleRepository = motorcycleRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    public boolean save(MotorcycleEntityDTO data) {
        MotorcycleEntity motorcycle = modelMapper.map(data, MotorcycleEntity.class);

        UserEntity owner = userService.getCurrentUser();
        motorcycle.setOwner(owner);
        motorcycle.setDaysActive(0);

        motorcycleRepository.save(motorcycle);

        return true;
    }

    public void delete(Long id) {
        motorcycleRepository.deleteById(id);
    }

    @Override
    public List<MotorcycleSummaryDTO> getAllMotorcyclesSummary() {
        return motorcycleRepository
                .findAll()
                .stream()
                .map(MotorcycleServiceImpl::toMotorcycleSummary)
                .toList();
    }

    private static MotorcycleSummaryDTO toMotorcycleSummary(MotorcycleEntity motorcycleEntity) {
        return new MotorcycleSummaryDTO(
                motorcycleEntity.getId(),
                motorcycleEntity.getName(),
                motorcycleEntity.getBrand(),
                motorcycleEntity.getModel(),
                motorcycleEntity.getPrice(),
                motorcycleEntity.getProductionYear(),
                motorcycleEntity.getHorsePower()
        );

    }


    private MotorcycleDetailsDTO toMotorcycleDetails(MotorcycleEntity motorcycleEntity) {
        return new MotorcycleDetailsDTO(
                motorcycleEntity.getId(),
                motorcycleEntity.getName(),
                motorcycleEntity.getBrand(),
                motorcycleEntity.getModel(),
                motorcycleEntity.getImageURL(),
                motorcycleEntity.getFuelConsumption(),
                motorcycleEntity.getDescription(),
                motorcycleEntity.getPrice(),
                motorcycleEntity.getProductionYear(),
                motorcycleEntity.getHorsePower(),
                motorcycleEntity.getOwner().getPhone()
        );
    }
    @Override
    public MotorcycleDetailsDTO getMotorcycleDetails(Long id) {

        return this.motorcycleRepository
                .findById(id)
                .map(this::toMotorcycleDetails)
                .orElseThrow(() -> new ObjectNotFoundException("Offer not found!", id));
    }

    public List<MotorcycleSummaryDTO> getMotorcyclesForCurrentUser() {
        Long ownerId = userService.getCurrentUser().getId();

        List<MotorcycleEntity> motorcycles = motorcycleRepository.getMotorcyclesByOwnerId(ownerId);

        return motorcycles.stream()
                .map(motorcycle -> modelMapper.map(motorcycle, MotorcycleSummaryDTO.class))
                .collect(Collectors.toList());
    }
}
