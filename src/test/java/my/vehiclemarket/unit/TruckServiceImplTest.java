package my.vehiclemarket.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import my.vehiclemarket.model.dto.TruckDetailsDTO;
import my.vehiclemarket.model.dto.TruckEntityDTO;
import my.vehiclemarket.model.dto.TruckSummaryDTO;
import my.vehiclemarket.model.entity.TruckEntity;
import my.vehiclemarket.model.entity.UserEntity;
import my.vehiclemarket.model.enums.TruckTypeEnum;
import my.vehiclemarket.repository.TruckRepository;
import my.vehiclemarket.service.exception.ObjectNotFoundException;
import my.vehiclemarket.service.impl.TruckServiceImpl;
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
public class TruckServiceImplTest {

    private TruckServiceImpl toTest;

    @Captor
    private ArgumentCaptor<TruckEntity> truckEntityCaptor;

    @Mock
    private TruckRepository mockTruckRepository;

    @Mock
    private UserServiceImpl mockUserService;

    @Mock
    private ModelMapper mockModelMapper;

    @BeforeEach
    void setUp() {
        toTest = new TruckServiceImpl(mockTruckRepository, mockModelMapper, mockUserService);
    }

    @Test
    void testSaveTruck() {
        TruckEntityDTO truckDTO = new TruckEntityDTO();
        truckDTO.setName("F-150");
        truckDTO.setBrand("Ford");
        truckDTO.setModel("2024");
        truckDTO.setPrice(29999.99);
        truckDTO.setProductionYear(2024);
        truckDTO.setTruckType(TruckTypeEnum.DUMP);
        truckDTO.setLoadCapacity(2000);

        UserEntity mockOwner = new UserEntity();
        when(mockUserService.getCurrentUser()).thenReturn(mockOwner);

        TruckEntity truckEntity = new TruckEntity();
        truckEntity.setName("F-150");
        truckEntity.setBrand("Ford");
        truckEntity.setModel("2024");
        truckEntity.setPrice(29999.99);
        truckEntity.setProductionYear(2024);
        truckEntity.setTruckType(TruckTypeEnum.DUMP);
        truckEntity.setLoadCapacity(2000);

        when(mockModelMapper.map(truckDTO, TruckEntity.class)).thenReturn(truckEntity);

        toTest.save(truckDTO);

        verify(mockTruckRepository).save(truckEntityCaptor.capture());
        TruckEntity savedTruckEntity = truckEntityCaptor.getValue();

        assertNotNull(savedTruckEntity);
        assertEquals(truckDTO.getName(), savedTruckEntity.getName());
        assertEquals(truckDTO.getBrand(), savedTruckEntity.getBrand());
        assertEquals(truckDTO.getModel(), savedTruckEntity.getModel());
        assertEquals(truckDTO.getPrice(), savedTruckEntity.getPrice());
        assertEquals(truckDTO.getProductionYear(), savedTruckEntity.getProductionYear());
        assertEquals(truckDTO.getTruckType(), savedTruckEntity.getTruckType());
        assertEquals(truckDTO.getLoadCapacity(), savedTruckEntity.getLoadCapacity());
        assertEquals(mockOwner, savedTruckEntity.getOwner());
        assertEquals(0, savedTruckEntity.getDaysActive());
    }

    @Test
    void testDeleteTruck() {
        toTest.delete(1L);
        verify(mockTruckRepository).deleteById(1L);
    }

    @Test
    void testGetTruckDetails() {
        UserEntity owner = new UserEntity();
        owner.setPhone("123-456-7890");

        TruckEntity truckEntity = new TruckEntity();
        truckEntity.setId(1L);
        truckEntity.setName("F-150");
        truckEntity.setBrand("Ford");
        truckEntity.setModel("2024");
        truckEntity.setImageURL("http://example.com/image.jpg");
        truckEntity.setFuelConsumption(15.0);
        truckEntity.setDescription("Heavy-duty pickup truck.");
        truckEntity.setPrice(29999.99);
        truckEntity.setProductionYear(2024);
        truckEntity.setTruckType(TruckTypeEnum.DUMP);
        truckEntity.setLoadCapacity(2000);
        truckEntity.setOwner(owner);

        when(mockTruckRepository.findById(1L)).thenReturn(Optional.of(truckEntity));

        TruckDetailsDTO result = toTest.getTruckDetails(1L);

        assertNotNull(result);
        assertEquals(truckEntity.getId(), result.getId());
        assertEquals(truckEntity.getName(), result.getName());
        assertEquals(truckEntity.getBrand(), result.getBrand());
        assertEquals(truckEntity.getModel(), result.getModel());
        assertEquals(truckEntity.getImageURL(), result.getImageURL());
        assertEquals(truckEntity.getFuelConsumption(), result.getFuelConsumption());
        assertEquals(truckEntity.getDescription(), result.getDescription());
        assertEquals(truckEntity.getPrice(), result.getPrice());
        assertEquals(truckEntity.getTruckType(), result.getTruckType());
        assertEquals(truckEntity.getLoadCapacity(), result.getLoadCapacity());
        assertEquals(truckEntity.getProductionYear(), result.getProductionYear());
        assertEquals(owner.getPhone(), result.getOwnerPhone());
    }

    @Test
    void testGetTruckDetailsNotFound() {
        when(mockTruckRepository.findById(1L)).thenReturn(Optional.empty());

        ObjectNotFoundException thrown = assertThrows(ObjectNotFoundException.class, () -> toTest.getTruckDetails(1L));

        assertEquals("Offer not found!", thrown.getMessage());
    }

    @Test
    void testGetAllTrucksSummary() {
        TruckEntity truck1 = new TruckEntity();
        truck1.setId(1L);
        truck1.setName("F-150");
        truck1.setBrand("Ford");
        truck1.setModel("2024");
        truck1.setPrice(29999.99);
        truck1.setProductionYear(2024);
        truck1.setTruckType(TruckTypeEnum.REFRIGERATOR);
        truck1.setLoadCapacity(2000);

        TruckEntity truck2 = new TruckEntity();
        truck2.setId(2L);
        truck2.setName("Ram 1500");
        truck2.setBrand("Dodge");
        truck2.setModel("2024");
        truck2.setPrice(31999.99);
        truck2.setProductionYear(2024);
        truck2.setTruckType(TruckTypeEnum.LORRY);
        truck2.setLoadCapacity(2500);

        when(mockTruckRepository.findAll()).thenReturn(Arrays.asList(truck1, truck2));

        List<TruckSummaryDTO> result = toTest.getAllTrucksSummary();

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(t -> t.getId().equals(1L) && "F-150".equals(t.getName())));
        assertTrue(result.stream().anyMatch(t -> t.getId().equals(2L) && "Ram 1500".equals(t.getName())));
    }

    @Test
    void testGetTrucksForCurrentUser() {
        TruckEntity truck1 = new TruckEntity();
        truck1.setId(1L);
        truck1.setName("F-150");
        truck1.setBrand("Ford");
        truck1.setModel("2024");
        truck1.setPrice(29999.99);
        truck1.setProductionYear(2024);
        truck1.setTruckType(TruckTypeEnum.REFRIGERATOR);
        truck1.setLoadCapacity(2000);

        TruckEntity truck2 = new TruckEntity();
        truck2.setId(2L);
        truck2.setName("Ram 1500");
        truck2.setBrand("Dodge");
        truck2.setModel("2024");
        truck2.setPrice(31999.99);
        truck2.setProductionYear(2024);
        truck2.setTruckType(TruckTypeEnum.TANK);
        truck2.setLoadCapacity(2500);

        TruckSummaryDTO dto1 = new TruckSummaryDTO();
        dto1.setId(1L);
        dto1.setName("F-150");

        TruckSummaryDTO dto2 = new TruckSummaryDTO();
        dto2.setId(2L);
        dto2.setName("Ram 1500");

        UserEntity currentUser = new UserEntity();
        currentUser.setId(1L);
        when(mockUserService.getCurrentUser()).thenReturn(currentUser);
        when(mockTruckRepository.getTrucksByOwnerId(1L)).thenReturn(Arrays.asList(truck1, truck2));

        when(mockModelMapper.map(truck1, TruckSummaryDTO.class)).thenReturn(dto1);
        when(mockModelMapper.map(truck2, TruckSummaryDTO.class)).thenReturn(dto2);

        List<TruckSummaryDTO> result = toTest.getTrucksForCurrentUser();

        assertNotNull(result);
        assertEquals(2, result.size());

        assertTrue(result.stream().anyMatch(t -> t.getId().equals(1L) && "F-150".equals(t.getName())));
        assertTrue(result.stream().anyMatch(t -> t.getId().equals(2L) && "Ram 1500".equals(t.getName())));
    }
}
