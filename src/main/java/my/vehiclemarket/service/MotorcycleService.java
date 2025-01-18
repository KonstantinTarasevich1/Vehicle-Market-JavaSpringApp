package my.vehiclemarket.service;

import my.vehiclemarket.model.dto.MotorcycleDetailsDTO;
import my.vehiclemarket.model.dto.MotorcycleSummaryDTO;

import java.util.List;

public interface MotorcycleService {
    MotorcycleDetailsDTO getMotorcycleDetails(Long id);

    List<MotorcycleSummaryDTO> getAllMotorcyclesSummary();
}
