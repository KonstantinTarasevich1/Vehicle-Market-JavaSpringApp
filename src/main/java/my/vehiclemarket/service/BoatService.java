package my.vehiclemarket.service;

import my.vehiclemarket.model.dto.BoatDetailsDTO;
import my.vehiclemarket.model.dto.BoatSummaryDTO;

import java.util.List;

public interface BoatService {
    BoatDetailsDTO getBoatDetails(Long id);

    List<BoatSummaryDTO> getAllBoatsSummary();
}
