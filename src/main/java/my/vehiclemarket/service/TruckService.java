package my.vehiclemarket.service;

import my.vehiclemarket.model.dto.MotorcycleDetailsDTO;
import my.vehiclemarket.model.dto.MotorcycleSummaryDTO;
import my.vehiclemarket.model.dto.TruckDetailsDTO;
import my.vehiclemarket.model.dto.TruckSummaryDTO;
import my.vehiclemarket.model.entity.TruckEntity;

import java.util.List;

public interface TruckService {
    TruckDetailsDTO getTruckDetails(Long id);

    List<TruckSummaryDTO> getAllTrucksSummary();

}
