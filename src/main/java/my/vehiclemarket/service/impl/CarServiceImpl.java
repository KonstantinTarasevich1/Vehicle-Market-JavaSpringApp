package my.vehiclemarket.service.impl;

import my.vehiclemarket.model.dto.*;
import my.vehiclemarket.model.entity.CarEntity;
import my.vehiclemarket.model.entity.UserEntity;
import my.vehiclemarket.model.enums.EngineTypeEnum;
import my.vehiclemarket.model.enums.TransmissionTypeEnum;
import my.vehiclemarket.repository.CarRepository;
import my.vehiclemarket.service.CarService;
import my.vehiclemarket.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final UserServiceImpl userService;


    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper, UserServiceImpl userService) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    public boolean save(CarEntityDTO data) {
        CarEntity car = modelMapper.map(data, CarEntity.class);

        UserEntity owner = userService.getCurrentUser();
        car.setOwner(owner);
        car.setDaysActive(0);

        if (car.getEngineType().equals(EngineTypeEnum.ELECTRIC)) {
            car.setFuelConsumption(0);
            car.setTransmissionType(TransmissionTypeEnum.AUTOMATIC);
        }

        carRepository.save(car);

        return true;
    }

    public void delete(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public List<CarSummaryDTO> getAllCarsSummary() {
        return carRepository
                .findAll()
                .stream()
                .map(CarServiceImpl::toCarSummary)
                .toList();
    }

    private static CarSummaryDTO toCarSummary(CarEntity carEntity) {
        return new CarSummaryDTO(
                carEntity.getId(),
                carEntity.getName(),
                carEntity.getBrand(),
                carEntity.getModel(),
                carEntity.getPrice(),
                carEntity.getProductionYear(),
                carEntity.getCarType(),
                carEntity.getHorsePower(),
                carEntity.getEngineType()
        );

    }


    private CarDetailsDTO toCarDetails(CarEntity carEntity) {
        return new CarDetailsDTO(
                carEntity.getId(),
                carEntity.getName(),
                carEntity.getBrand(),
                carEntity.getModel(),
                carEntity.getImageURL(),
                carEntity.getFuelConsumption(),
                carEntity.getDescription(),
                carEntity.getPrice(),
                carEntity.getTransmissionType(),
                carEntity.getCarType(),
                carEntity.getEngineType(),
                carEntity.getHorsePower(),
                carEntity.getProductionYear(),
                carEntity.getOwner().getPhone()
        );
    }
    @Override
    public CarDetailsDTO getCarDetails(Long id) {

        return this.carRepository
                .findById(id)
                .map(this::toCarDetails)
                .orElseThrow(() -> new ObjectNotFoundException("Offer not found!", id));
    }

    public List<CarSummaryDTO> getCarsForCurrentUser() {
        Long ownerId = userService.getCurrentUser().getId();

        List<CarEntity> cars = carRepository.getCarsByOwnerId(ownerId);

        return cars.stream()
                .map(car -> modelMapper.map(car, CarSummaryDTO.class))
                .collect(Collectors.toList());
    }
}
