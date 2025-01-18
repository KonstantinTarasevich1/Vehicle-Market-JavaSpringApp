package my.exratesservice.unit;

import my.exratesservice.model.entity.RateEntity;
import my.exratesservice.repository.RateRepository;
import my.exratesservice.service.RateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RateServiceCrudTest {

    @Mock
    private RateRepository rateRepository;

    @InjectMocks
    private RateService rateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllRates() {
        // Arrange
        List<RateEntity> mockRates = new ArrayList<>();
        mockRates.add(new RateEntity()); // Add some mock data if needed
        when(rateRepository.findAll()).thenReturn(mockRates);

        // Act
        Iterable<RateEntity> rates = rateService.getAllRates();

        // Assert
        verify(rateRepository).findAll();
        assertEquals(mockRates, rates);
    }

    @Test
    void testSaveRate() {
        // Arrange
        RateEntity rate = new RateEntity();
        when(rateRepository.save(rate)).thenReturn(rate);

        // Act
        RateEntity savedRate = rateService.saveRate(rate);

        // Assert
        verify(rateRepository).save(rate);
        assertSame(rate, savedRate);
    }

    @Test
    void testDeleteRate() {
        // Arrange
        Long rateId = 1L;

        // Act
        rateService.deleteRate(rateId);

        // Assert
        verify(rateRepository).deleteById(rateId);
    }
}

