package my.vehiclemarket.scheduler;

import my.vehiclemarket.repository.BoatRepository;
import my.vehiclemarket.repository.CarRepository;
import my.vehiclemarket.repository.MotorcycleRepository;
import my.vehiclemarket.repository.TruckRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class VehicleScheduler {

    private final CarRepository carRepository;
    private final BoatRepository boatRepository;
    private final MotorcycleRepository motorcycleRepository;
    private final TruckRepository truckRepository;

    public VehicleScheduler(CarRepository carRepository, BoatRepository boatRepository, MotorcycleRepository motorcycleRepository, TruckRepository truckRepository) {
        this.carRepository = carRepository;
        this.boatRepository = boatRepository;
        this.motorcycleRepository = motorcycleRepository;
        this.truckRepository = truckRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void incrementDaysActive() {
        carRepository.incrementDaysActiveForAll();
        boatRepository.incrementDaysActiveForAll();
        motorcycleRepository.incrementDaysActiveForAll();
        truckRepository.incrementDaysActiveForAll();
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteExpiredVehicles() {
        carRepository.deleteVehiclesWithDaysActiveGreaterThan(60);
        boatRepository.deleteVehiclesWithDaysActiveGreaterThan(60);
        motorcycleRepository.deleteVehiclesWithDaysActiveGreaterThan(60);
        truckRepository.deleteVehiclesWithDaysActiveGreaterThan(60);
    }
}
