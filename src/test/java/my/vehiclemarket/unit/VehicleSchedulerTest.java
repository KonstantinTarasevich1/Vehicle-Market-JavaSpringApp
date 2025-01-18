package my.vehiclemarket.unit;

import my.vehiclemarket.repository.BoatRepository;
import my.vehiclemarket.repository.CarRepository;
import my.vehiclemarket.repository.MotorcycleRepository;
import my.vehiclemarket.repository.TruckRepository;
import my.vehiclemarket.scheduler.VehicleScheduler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleSchedulerTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private BoatRepository boatRepository;

    @Mock
    private MotorcycleRepository motorcycleRepository;

    @Mock
    private TruckRepository truckRepository;

    @InjectMocks
    private VehicleScheduler vehicleScheduler;

    @Test
    void testIncrementDaysActive() {
        vehicleScheduler.incrementDaysActive();

        verify(carRepository, times(1)).incrementDaysActiveForAll();
        verify(boatRepository, times(1)).incrementDaysActiveForAll();
        verify(motorcycleRepository, times(1)).incrementDaysActiveForAll();
        verify(truckRepository, times(1)).incrementDaysActiveForAll();
    }

    @Test
    void testDeleteExpiredVehicles() {
        vehicleScheduler.deleteExpiredVehicles();

        verify(carRepository, times(1)).deleteVehiclesWithDaysActiveGreaterThan(60);
        verify(boatRepository, times(1)).deleteVehiclesWithDaysActiveGreaterThan(60);
        verify(motorcycleRepository, times(1)).deleteVehiclesWithDaysActiveGreaterThan(60);
        verify(truckRepository, times(1)).deleteVehiclesWithDaysActiveGreaterThan(60);
    }
}
