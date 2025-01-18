package my.vehiclemarket.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import my.vehiclemarket.model.dto.MotorcycleDetailsDTO;
import my.vehiclemarket.model.dto.MotorcycleEntityDTO;
import my.vehiclemarket.model.dto.MotorcycleSummaryDTO;
import my.vehiclemarket.model.entity.MotorcycleEntity;
import my.vehiclemarket.model.entity.UserEntity;
import my.vehiclemarket.repository.MotorcycleRepository;
import my.vehiclemarket.service.exception.ObjectNotFoundException;
import my.vehiclemarket.service.impl.MotorcycleServiceImpl;
import my.vehiclemarket.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Captor;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MotorcycleServiceImplTest {

    private MotorcycleServiceImpl toTest;

    @Captor
    private ArgumentCaptor<MotorcycleEntity> motorcycleEntityCaptor;

    @Mock
    private MotorcycleRepository mockMotorcycleRepository;

    @Mock
    private UserServiceImpl mockUserService;

    @Mock
    private ModelMapper mockModelMapper;

    @BeforeEach
    void setUp() {
        toTest = new MotorcycleServiceImpl(mockMotorcycleRepository, mockModelMapper, mockUserService);
    }

    @Test
    void testSaveMotorcycle() {
        MotorcycleEntityDTO motorcycleDTO = new MotorcycleEntityDTO();
        motorcycleDTO.setName("Ninja");
        motorcycleDTO.setBrand("Kawasaki");
        motorcycleDTO.setModel("ZX-10R");
        motorcycleDTO.setPrice(15999.99);
        motorcycleDTO.setProductionYear(2024);
        motorcycleDTO.setHorsePower(200);

        UserEntity mockOwner = new UserEntity();
        when(mockUserService.getCurrentUser()).thenReturn(mockOwner);

        MotorcycleEntity motorcycleEntity = new MotorcycleEntity();
        motorcycleEntity.setName("Ninja");
        motorcycleEntity.setBrand("Kawasaki");
        motorcycleEntity.setModel("ZX-10R");
        motorcycleEntity.setPrice(15999.99);
        motorcycleEntity.setProductionYear(2024);
        motorcycleEntity.setHorsePower(200);

        when(mockModelMapper.map(motorcycleDTO, MotorcycleEntity.class)).thenReturn(motorcycleEntity);

        toTest.save(motorcycleDTO);

        verify(mockMotorcycleRepository).save(motorcycleEntityCaptor.capture());
        MotorcycleEntity savedMotorcycleEntity = motorcycleEntityCaptor.getValue();

        assertNotNull(savedMotorcycleEntity);
        assertEquals(motorcycleDTO.getName(), savedMotorcycleEntity.getName());
        assertEquals(motorcycleDTO.getBrand(), savedMotorcycleEntity.getBrand());
        assertEquals(motorcycleDTO.getModel(), savedMotorcycleEntity.getModel());
        assertEquals(motorcycleDTO.getPrice(), savedMotorcycleEntity.getPrice());
        assertEquals(motorcycleDTO.getProductionYear(), savedMotorcycleEntity.getProductionYear());
        assertEquals(motorcycleDTO.getHorsePower(), savedMotorcycleEntity.getHorsePower());
        assertEquals(mockOwner, savedMotorcycleEntity.getOwner());
        assertEquals(0, savedMotorcycleEntity.getDaysActive());
    }

    @Test
    void testDeleteMotorcycle() {
        toTest.delete(1L);
        verify(mockMotorcycleRepository).deleteById(1L);
    }

    @Test
    void testGetMotorcycleDetails() {
        UserEntity owner = new UserEntity();
        owner.setPhone("123-456-7890");

        MotorcycleEntity motorcycleEntity = new MotorcycleEntity();
        motorcycleEntity.setId(1L);
        motorcycleEntity.setName("Ninja");
        motorcycleEntity.setBrand("Kawasaki");
        motorcycleEntity.setModel("ZX-10R");
        motorcycleEntity.setImageURL("http://example.com/image.jpg");
        motorcycleEntity.setFuelConsumption(5.5);
        motorcycleEntity.setDescription("High-performance motorcycle.");
        motorcycleEntity.setPrice(15999.99);
        motorcycleEntity.setProductionYear(2024);
        motorcycleEntity.setHorsePower(200);
        motorcycleEntity.setOwner(owner);

        when(mockMotorcycleRepository.findById(1L)).thenReturn(Optional.of(motorcycleEntity));

        MotorcycleDetailsDTO result = toTest.getMotorcycleDetails(1L);

        assertNotNull(result);
        assertEquals(motorcycleEntity.getId(), result.getId());
        assertEquals(motorcycleEntity.getName(), result.getName());
        assertEquals(motorcycleEntity.getBrand(), result.getBrand());
        assertEquals(motorcycleEntity.getModel(), result.getModel());
        assertEquals(motorcycleEntity.getImageURL(), result.getImageURL());
        assertEquals(motorcycleEntity.getFuelConsumption(), result.getFuelConsumption());
        assertEquals(motorcycleEntity.getDescription(), result.getDescription());
        assertEquals(motorcycleEntity.getPrice(), result.getPrice());
        assertEquals(motorcycleEntity.getProductionYear(), result.getProductionYear());
        assertEquals(motorcycleEntity.getHorsePower(), result.getHorsePower());
        assertEquals(owner.getPhone(), result.getOwnerPhone());
    }

    @Test
    void testGetMotorcycleDetailsNotFound() {
        when(mockMotorcycleRepository.findById(1L)).thenReturn(Optional.empty());

        ObjectNotFoundException thrown = assertThrows(ObjectNotFoundException.class, () -> toTest.getMotorcycleDetails(1L));

        assertEquals("Offer not found!", thrown.getMessage());
    }

    @Test
    void testGetAllMotorcyclesSummary() {
        MotorcycleEntity motorcycle1 = new MotorcycleEntity();
        motorcycle1.setId(1L);
        motorcycle1.setName("Ninja");
        motorcycle1.setBrand("Kawasaki");
        motorcycle1.setModel("ZX-10R");
        motorcycle1.setPrice(15999.99);
        motorcycle1.setProductionYear(2024);
        motorcycle1.setHorsePower(200);

        MotorcycleEntity motorcycle2 = new MotorcycleEntity();
        motorcycle2.setId(2L);
        motorcycle2.setName("R1");
        motorcycle2.setBrand("Yamaha");
        motorcycle2.setModel("YZF-R1");
        motorcycle2.setPrice(16999.99);
        motorcycle2.setProductionYear(2024);
        motorcycle2.setHorsePower(200);

        when(mockMotorcycleRepository.findAll()).thenReturn(Arrays.asList(motorcycle1, motorcycle2));

        List<MotorcycleSummaryDTO> result = toTest.getAllMotorcyclesSummary();

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(m -> m.getId().equals(1L) && "Ninja".equals(m.getName())));
        assertTrue(result.stream().anyMatch(m -> m.getId().equals(2L) && "R1".equals(m.getName())));
    }

    @Test
    void testGetMotorcyclesForCurrentUser() {
        MotorcycleEntity motorcycle1 = new MotorcycleEntity();
        motorcycle1.setId(1L);
        motorcycle1.setName("Ninja");
        motorcycle1.setBrand("Kawasaki");
        motorcycle1.setModel("ZX-10R");
        motorcycle1.setPrice(15999.99);
        motorcycle1.setProductionYear(2024);
        motorcycle1.setHorsePower(200);

        MotorcycleEntity motorcycle2 = new MotorcycleEntity();
        motorcycle2.setId(2L);
        motorcycle2.setName("R1");
        motorcycle2.setBrand("Yamaha");
        motorcycle2.setModel("YZF-R1");
        motorcycle2.setPrice(16999.99);
        motorcycle2.setProductionYear(2024);
        motorcycle2.setHorsePower(200);

        MotorcycleSummaryDTO dto1 = new MotorcycleSummaryDTO();
        dto1.setId(1L);
        dto1.setName("Ninja");

        MotorcycleSummaryDTO dto2 = new MotorcycleSummaryDTO();
        dto2.setId(2L);
        dto2.setName("R1");

        UserEntity currentUser = new UserEntity();
        currentUser.setId(1L);
        when(mockUserService.getCurrentUser()).thenReturn(currentUser);
        when(mockMotorcycleRepository.getMotorcyclesByOwnerId(1L)).thenReturn(Arrays.asList(motorcycle1, motorcycle2));

        when(mockModelMapper.map(motorcycle1, MotorcycleSummaryDTO.class)).thenReturn(dto1);
        when(mockModelMapper.map(motorcycle2, MotorcycleSummaryDTO.class)).thenReturn(dto2);

        List<MotorcycleSummaryDTO> result = toTest.getMotorcyclesForCurrentUser();

        assertNotNull(result);
        assertEquals(2, result.size());

        assertTrue(result.stream().anyMatch(m -> m.getId().equals(1L) && "Ninja".equals(m.getName())));
        assertTrue(result.stream().anyMatch(m -> m.getId().equals(2L) && "R1".equals(m.getName())));
    }
}
