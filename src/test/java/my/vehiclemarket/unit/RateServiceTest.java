package my.vehiclemarket.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import my.vehiclemarket.model.entity.RateEntity;
import my.vehiclemarket.service.impl.RateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class RateServiceTest {

    private RateService rateService;

    @Mock
    private RestTemplate mockRestTemplate;

    @BeforeEach
    void setUp() {
        rateService = new RateService(mockRestTemplate);
    }

    @Test
    void testGetAllRates() {
        RateEntity rate1 = new RateEntity();
        rate1.setId(1L);
        rate1.setRate(1.0);

        RateEntity rate2 = new RateEntity();
        rate2.setId(2L);
        rate2.setRate(1.5);

        RateEntity[] ratesArray = {rate1, rate2};

        when(mockRestTemplate.getForObject("http://localhost:8081/rates", RateEntity[].class))
                .thenReturn(ratesArray);

        List<RateEntity> result = rateService.getAllRates();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(1.0, result.get(0).getRate());
        assertEquals(2L, result.get(1).getId());
        assertEquals(1.5, result.get(1).getRate());
    }

    @Test
    void testSaveRate() {
        RateEntity rate = new RateEntity();
        rate.setId(1L);
        rate.setRate(1.0);

        when(mockRestTemplate.postForObject("http://localhost:8081/rates", rate, RateEntity.class))
                .thenReturn(rate);

        RateEntity result = rateService.saveRate(rate);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1.0, result.getRate());

        verify(mockRestTemplate).postForObject("http://localhost:8081/rates", rate, RateEntity.class);
    }

    @Test
    void testDeleteRate() {
        Long rateId = 1L;

        doNothing().when(mockRestTemplate).delete("http://localhost:8081/rates/" + rateId);

        rateService.deleteRate(rateId);

        verify(mockRestTemplate).delete("http://localhost:8081/rates/" + rateId);
    }
}
