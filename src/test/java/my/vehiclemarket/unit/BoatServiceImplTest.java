package my.vehiclemarket.unit;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import my.vehiclemarket.model.dto.BoatDetailsDTO;
import my.vehiclemarket.model.dto.BoatEntityDTO;
import my.vehiclemarket.model.dto.BoatSummaryDTO;
import my.vehiclemarket.model.entity.BoatEntity;
import my.vehiclemarket.model.entity.UserEntity;
import my.vehiclemarket.model.enums.BoatTypeEnum;
import my.vehiclemarket.repository.BoatRepository;
import my.vehiclemarket.service.impl.BoatServiceImpl;
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
public class BoatServiceImplTest {

    private BoatServiceImpl toTest;

    @Captor
    private ArgumentCaptor<BoatEntity> boatEntityCaptor;

    @Mock
    private BoatRepository mockBoatRepository;

    @Mock
    private UserServiceImpl mockUserService;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        toTest = new BoatServiceImpl(mockBoatRepository, modelMapper, mockUserService);
    }

    @Test
    void testSaveBoat() {

        BoatEntityDTO boatEntityDTO = new BoatEntityDTO();
        boatEntityDTO.setName("Speedster");
        boatEntityDTO.setBrand("Yamaha");
        boatEntityDTO.setModel("X1");
        boatEntityDTO.setPrice(50000.0);
        boatEntityDTO.setProductionYear(2022);
        boatEntityDTO.setBoatType(BoatTypeEnum.SPEEDBOAT);

        UserEntity mockOwner = new UserEntity();
        when(mockUserService.getCurrentUser()).thenReturn(mockOwner);

        toTest.save(boatEntityDTO);

        verify(mockBoatRepository).save(boatEntityCaptor.capture());
        BoatEntity savedBoatEntity = boatEntityCaptor.getValue();

        assertNotNull(savedBoatEntity);
        assertEquals(boatEntityDTO.getName(), savedBoatEntity.getName());
        assertEquals(boatEntityDTO.getBrand(), savedBoatEntity.getBrand());
        assertEquals(boatEntityDTO.getModel(), savedBoatEntity.getModel());
        assertEquals(boatEntityDTO.getPrice(), savedBoatEntity.getPrice());
        assertEquals(boatEntityDTO.getProductionYear(), savedBoatEntity.getProductionYear());
        assertEquals(boatEntityDTO.getBoatType(), savedBoatEntity.getBoatType());
        assertEquals(mockOwner, savedBoatEntity.getOwner());
        assertEquals(0, savedBoatEntity.getDaysActive());
    }

    @Test
    void testDeleteBoat() {

        toTest.delete(1L);

        verify(mockBoatRepository).deleteById(1L);
    }

    @Test
    void testGetBoatDetails() {
        UserEntity owner = new UserEntity();
        owner.setPhone("123456789");

        BoatEntity boatEntity = new BoatEntity();
        boatEntity.setId(1L);
        boatEntity.setName("Speedster");
        boatEntity.setBrand("Yamaha");
        boatEntity.setModel("X1");
        boatEntity.setImageURL("http://example.com/image.jpg");
        boatEntity.setFuelConsumption(1.5);
        boatEntity.setDescription("Fast and reliable.");
        boatEntity.setPrice(50000.0);
        boatEntity.setProductionYear(2022);
        boatEntity.setBoatType(BoatTypeEnum.SPEEDBOAT);
        boatEntity.setOwner(owner);

        when(mockBoatRepository.findById(1L)).thenReturn(Optional.of(boatEntity));

        BoatDetailsDTO result = toTest.getBoatDetails(1L);

        assertNotNull(result);
        assertEquals(boatEntity.getId(), result.getId());
        assertEquals(boatEntity.getName(), result.getName());
        assertEquals(boatEntity.getBrand(), result.getBrand());
        assertEquals(boatEntity.getModel(), result.getModel());
        assertEquals(boatEntity.getImageURL(), result.getImageURL());
        assertEquals(boatEntity.getFuelConsumption(), result.getFuelConsumption());
        assertEquals(boatEntity.getDescription(), result.getDescription());
        assertEquals(boatEntity.getPrice(), result.getPrice());
        assertEquals(boatEntity.getProductionYear(), result.getProductionYear());
        assertEquals(boatEntity.getBoatType(), result.getBoatType());
        assertEquals(owner.getPhone(), result.getOwnerPhone()); // Ensure this matches the expected result
    }

    @Test
    void testGetAllBoatsSummary() {
        BoatEntity boat1 = new BoatEntity();
        boat1.setId(1L);
        boat1.setName("Speedster");
        boat1.setBrand("Yamaha");
        boat1.setModel("X1");
        boat1.setPrice(50000.0);
        boat1.setProductionYear(2022);
        boat1.setBoatType(BoatTypeEnum.SPEEDBOAT);

        BoatEntity boat2 = new BoatEntity();
        boat2.setId(2L);
        boat2.setName("Cruiser");
        boat2.setBrand("SeaRay");
        boat2.setModel("C2");
        boat2.setPrice(60000.0);
        boat2.setProductionYear(2023);
        boat2.setBoatType(BoatTypeEnum.SAILBOAT);

        when(mockBoatRepository.findAll()).thenReturn(Arrays.asList(boat1, boat2));

        List<BoatSummaryDTO> result = toTest.getAllBoatsSummary();

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(b -> b.getId().equals(1L) && "Speedster".equals(b.getName())));
        assertTrue(result.stream().anyMatch(b -> b.getId().equals(2L) && "Cruiser".equals(b.getName())));
    }

    @Test
    void testGetBoatsForCurrentUser() {

        BoatEntity boat1 = new BoatEntity();
        boat1.setId(1L);
        boat1.setName("Speedster");
        boat1.setBrand("Yamaha");
        boat1.setModel("X1");
        boat1.setPrice(50000.0);
        boat1.setProductionYear(2022);
        boat1.setBoatType(BoatTypeEnum.SPEEDBOAT);

        BoatEntity boat2 = new BoatEntity();
        boat2.setId(2L);
        boat2.setName("Cruiser");
        boat2.setBrand("SeaRay");
        boat2.setModel("C2");
        boat2.setPrice(60000.0);
        boat2.setProductionYear(2023);
        boat2.setBoatType(BoatTypeEnum.SAILBOAT);

        UserEntity currentUser = new UserEntity();
        currentUser.setId(1L);
        when(mockUserService.getCurrentUser()).thenReturn(currentUser);
        when(mockBoatRepository.getBoatsByOwnerId(1L)).thenReturn(Arrays.asList(boat1, boat2));

        List<BoatSummaryDTO> result = toTest.getBoatsForCurrentUser();

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(b -> b.getId().equals(1L) && "Speedster".equals(b.getName())));
        assertTrue(result.stream().anyMatch(b -> b.getId().equals(2L) && "Cruiser".equals(b.getName())));
    }
}
