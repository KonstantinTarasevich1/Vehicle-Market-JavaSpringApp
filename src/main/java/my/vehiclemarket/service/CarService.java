package my.vehiclemarket.service;


import my.vehiclemarket.model.dto.CarDetailsDTO;
import my.vehiclemarket.model.dto.CarSummaryDTO;

import java.util.List;

public interface CarService {
    CarDetailsDTO getCarDetails(Long id);

    List<CarSummaryDTO> getAllCarsSummary();
}
