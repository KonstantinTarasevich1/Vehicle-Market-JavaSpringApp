package my.vehiclemarket.service.impl;

import my.vehiclemarket.model.dto.BoatDetailsDTO;
import my.vehiclemarket.model.dto.BoatEntityDTO;
import my.vehiclemarket.model.dto.BoatSummaryDTO;
import my.vehiclemarket.model.dto.TruckSummaryDTO;
import my.vehiclemarket.model.entity.BoatEntity;
import my.vehiclemarket.model.entity.TruckEntity;
import my.vehiclemarket.model.entity.UserEntity;
import my.vehiclemarket.repository.BoatRepository;
import my.vehiclemarket.service.BoatService;
import my.vehiclemarket.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoatServiceImpl implements BoatService {

    private final BoatRepository boatRepository;
    private final ModelMapper modelMapper;
    private final UserServiceImpl userService;

    public BoatServiceImpl(BoatRepository boatRepository, ModelMapper modelMapper, UserServiceImpl userService) {
        this.boatRepository = boatRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    public boolean save(BoatEntityDTO data) {


        BoatEntity boat = modelMapper.map(data, BoatEntity.class);

        UserEntity owner = userService.getCurrentUser();
        boat.setOwner(owner);
        boat.setDaysActive(0);

        boatRepository.save(boat);

        return true;
    }

    public void delete(Long id) {
        boatRepository.deleteById(id);
    }

    @Override
    public BoatDetailsDTO getBoatDetails(Long id) {

        return this.boatRepository
                .findById(id)
                .map(this::toBoatDetails)
                .orElseThrow(() -> new ObjectNotFoundException("Offer not found!", id));
    }

    @Override
    public List<BoatSummaryDTO> getAllBoatsSummary() {
        return boatRepository
                .findAll()
                .stream()
                .map(BoatServiceImpl::toBoatSummary)
                .toList();
    }

    private static BoatSummaryDTO toBoatSummary(BoatEntity boatEntity) {
        return new BoatSummaryDTO(
                boatEntity.getId(),
                boatEntity.getName(),
                boatEntity.getBrand(),
                boatEntity.getModel(),
                boatEntity.getPrice(),
                boatEntity.getProductionYear(),
                boatEntity.getBoatType()
                );

    }


    private BoatDetailsDTO toBoatDetails(BoatEntity boatEntity) {
        return new BoatDetailsDTO(
                boatEntity.getId(),
                boatEntity.getName(),
                boatEntity.getBrand(),
                boatEntity.getModel(),
                boatEntity.getImageURL(),
                boatEntity.getFuelConsumption(),
                boatEntity.getDescription(),
                boatEntity.getPrice(),
                boatEntity.getProductionYear(),
                boatEntity.getBoatType(),
                boatEntity.getOwner().getPhone()
        );
    }

    public List<BoatSummaryDTO> getBoatsForCurrentUser() {
        Long ownerId = userService.getCurrentUser().getId();

        List<BoatEntity> boats = boatRepository.getBoatsByOwnerId(ownerId);

        return boats.stream()
                .map(boat -> modelMapper.map(boat, BoatSummaryDTO.class))
                .collect(Collectors.toList());
    }
}
