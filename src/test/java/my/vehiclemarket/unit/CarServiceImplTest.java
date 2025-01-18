package my.vehiclemarket.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import my.vehiclemarket.model.dto.CarDetailsDTO;
import my.vehiclemarket.model.dto.CarSummaryDTO;
import my.vehiclemarket.model.dto.CarEntityDTO;
import my.vehiclemarket.model.entity.CarEntity;
import my.vehiclemarket.model.entity.UserEntity;
import my.vehiclemarket.model.enums.CarTypeEnum;
import my.vehiclemarket.model.enums.EngineTypeEnum;
import my.vehiclemarket.model.enums.TransmissionTypeEnum;
import my.vehiclemarket.repository.CarRepository;
import my.vehiclemarket.repository.UserRepository;
import my.vehiclemarket.service.exception.ObjectNotFoundException;
import my.vehiclemarket.service.impl.CarServiceImpl;
import my.vehiclemarket.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class CarServiceImplTest {

    private CarServiceImpl toTest;

    @Captor
    private ArgumentCaptor<CarEntity> carEntityCaptor;

    @Mock
    private CarRepository mockCarRepository;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private UserServiceImpl mockUserService;

    @Mock
    private ModelMapper mockModelMapper;

    @BeforeEach
    void setUp() {
        toTest = new CarServiceImpl(mockCarRepository, mockModelMapper, mockUserService);
    }

    @Test
    void testSaveCar() {

        CarEntityDTO carDTO = new CarEntityDTO();
        carDTO.setName("Model S");
        carDTO.setBrand("Tesla");
        carDTO.setModel("2024");
        carDTO.setPrice(79999.99);
        carDTO.setProductionYear(2024);
        carDTO.setCarType(CarTypeEnum.SEDAN);
        carDTO.setEngineType(EngineTypeEnum.ELECTRIC);
        carDTO.setHorsePower(500);
        carDTO.setTransmissionType(TransmissionTypeEnum.AUTOMATIC);

        UserEntity mockOwner = new UserEntity();
        when(mockUserService.getCurrentUser()).thenReturn(mockOwner);

        CarEntity carEntity = new CarEntity();
        carEntity.setName("Model S");
        carEntity.setBrand("Tesla");
        carEntity.setModel("2024");
        carEntity.setPrice(79999.99);
        carEntity.setProductionYear(2024);
        carEntity.setCarType(CarTypeEnum.SEDAN);
        carEntity.setEngineType(EngineTypeEnum.ELECTRIC);
        carEntity.setHorsePower(500);
        carEntity.setTransmissionType(TransmissionTypeEnum.AUTOMATIC);

        when(mockModelMapper.map(carDTO, CarEntity.class)).thenReturn(carEntity);

        toTest.save(carDTO);

        verify(mockCarRepository).save(carEntityCaptor.capture());
        CarEntity savedCarEntity = carEntityCaptor.getValue();

        assertNotNull(savedCarEntity);
        assertEquals(carDTO.getName(), savedCarEntity.getName());
        assertEquals(carDTO.getBrand(), savedCarEntity.getBrand());
        assertEquals(carDTO.getModel(), savedCarEntity.getModel());
        assertEquals(carDTO.getPrice(), savedCarEntity.getPrice());
        assertEquals(carDTO.getProductionYear(), savedCarEntity.getProductionYear());
        assertEquals(carDTO.getCarType(), savedCarEntity.getCarType());
        assertEquals(carDTO.getEngineType(), savedCarEntity.getEngineType());
        assertEquals(carDTO.getHorsePower(), savedCarEntity.getHorsePower());
        assertEquals(mockOwner, savedCarEntity.getOwner());
        assertEquals(0, savedCarEntity.getDaysActive());

        assertEquals(TransmissionTypeEnum.AUTOMATIC, savedCarEntity.getTransmissionType());
    }


    @Test
    void testDeleteCar() {
        toTest.delete(1L);
        verify(mockCarRepository).deleteById(1L);
    }

    @Test
    void testGetCarDetails() {
        UserEntity owner = new UserEntity();
        owner.setPhone("123-456-7890");

        CarEntity carEntity = new CarEntity();
        carEntity.setId(1L);
        carEntity.setName("Model S");
        carEntity.setBrand("Tesla");
        carEntity.setModel("2024");
        carEntity.setImageURL("http://example.com/image.jpg");
        carEntity.setFuelConsumption(0.0);
        carEntity.setDescription("Electric car with autopilot.");
        carEntity.setPrice(79999.99);
        carEntity.setProductionYear(2024);
        carEntity.setCarType(CarTypeEnum.SEDAN);
        carEntity.setEngineType(EngineTypeEnum.ELECTRIC);
        carEntity.setHorsePower(500);
        carEntity.setTransmissionType(TransmissionTypeEnum.AUTOMATIC);
        carEntity.setOwner(owner);

        when(mockCarRepository.findById(1L)).thenReturn(Optional.of(carEntity));

        CarDetailsDTO result = toTest.getCarDetails(1L);

        assertNotNull(result);
        assertEquals(carEntity.getId(), result.getId());
        assertEquals(carEntity.getName(), result.getName());
        assertEquals(carEntity.getBrand(), result.getBrand());
        assertEquals(carEntity.getModel(), result.getModel());
        assertEquals(carEntity.getImageURL(), result.getImageURL());
        assertEquals(carEntity.getFuelConsumption(), result.getFuelConsumption());
        assertEquals(carEntity.getDescription(), result.getDescription());
        assertEquals(carEntity.getPrice(), result.getPrice());
        assertEquals(carEntity.getTransmissionType(), result.getTransmissionType());
        assertEquals(carEntity.getCarType(), result.getCarType());
        assertEquals(carEntity.getEngineType(), result.getEngineType());
        assertEquals(carEntity.getHorsePower(), result.getHorsePower());
        assertEquals(carEntity.getProductionYear(), result.getProductionYear());
        assertEquals(owner.getPhone(), result.getOwnerPhone());
    }

    @Test
    void testGetCarDetailsNotFound() {
        when(mockCarRepository.findById(1L)).thenReturn(Optional.empty());

        ObjectNotFoundException thrown = assertThrows(ObjectNotFoundException.class, () -> {
            toTest.getCarDetails(1L);
        });

        assertEquals("Offer not found!", thrown.getMessage());
    }

    @Test
    void testGetAllCarsSummary() {
        CarEntity car1 = new CarEntity();
        car1.setId(1L);
        car1.setName("Model S");
        car1.setBrand("Tesla");
        car1.setModel("2024");
        car1.setPrice(79999.99);
        car1.setProductionYear(2024);
        car1.setCarType(CarTypeEnum.SEDAN);
        car1.setHorsePower(500);
        car1.setEngineType(EngineTypeEnum.ELECTRIC);

        CarEntity car2 = new CarEntity();
        car2.setId(2L);
        car2.setName("Mustang");
        car2.setBrand("Ford");
        car2.setModel("2024");
        car2.setPrice(55999.99);
        car2.setProductionYear(2024);
        car2.setCarType(CarTypeEnum.COUPE);
        car2.setHorsePower(450);
        car2.setEngineType(EngineTypeEnum.GASOLINE);

        when(mockCarRepository.findAll()).thenReturn(Arrays.asList(car1, car2));

        List<CarSummaryDTO> result = toTest.getAllCarsSummary();

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(c -> c.getId().equals(1L) && "Model S".equals(c.getName())));
        assertTrue(result.stream().anyMatch(c -> c.getId().equals(2L) && "Mustang".equals(c.getName())));
    }

    @Test
    void testGetCarsForCurrentUser() {

        CarEntity car1 = new CarEntity();
        car1.setId(1L);
        car1.setName("Model S");
        car1.setBrand("Tesla");
        car1.setModel("2024");
        car1.setPrice(79999.99);
        car1.setProductionYear(2024);
        car1.setCarType(CarTypeEnum.SEDAN);
        car1.setEngineType(EngineTypeEnum.ELECTRIC);
        car1.setHorsePower(500);
        car1.setTransmissionType(TransmissionTypeEnum.AUTOMATIC);

        CarEntity car2 = new CarEntity();
        car2.setId(2L);
        car2.setName("Model X");
        car2.setBrand("Tesla");
        car2.setModel("2024");
        car2.setPrice(89999.99);
        car2.setProductionYear(2024);
        car2.setCarType(CarTypeEnum.SUV);
        car2.setEngineType(EngineTypeEnum.ELECTRIC);
        car2.setHorsePower(600);
        car2.setTransmissionType(TransmissionTypeEnum.AUTOMATIC);

        CarSummaryDTO dto1 = new CarSummaryDTO();
        dto1.setId(1L);
        dto1.setName("Speedster");

        CarSummaryDTO dto2 = new CarSummaryDTO();
        dto2.setId(2L);
        dto2.setName("Cruiser");

        UserEntity currentUser = new UserEntity();
        currentUser.setId(1L);
        when(mockUserService.getCurrentUser()).thenReturn(currentUser);
        when(mockCarRepository.getCarsByOwnerId(1L)).thenReturn(Arrays.asList(car1, car2));

        when(mockModelMapper.map(car1, CarSummaryDTO.class)).thenReturn(dto1);
        when(mockModelMapper.map(car2, CarSummaryDTO.class)).thenReturn(dto2);

        List<CarSummaryDTO> result = toTest.getCarsForCurrentUser();

        assertNotNull(result);
        assertEquals(2, result.size());

        assertTrue(result.stream().anyMatch(c -> c.getId().equals(1L) && "Speedster".equals(c.getName())));
        assertTrue(result.stream().anyMatch(c -> c.getId().equals(2L) && "Cruiser".equals(c.getName())));
    }

}
