package my.exratesservice.unit;

import my.exratesservice.model.entity.RateEntity;
import my.exratesservice.service.RateService;
import my.exratesservice.repository.RateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

class RateServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private RateRepository rateRepository;

    @InjectMocks
    private RateService rateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFetchAndSaveRatesSuccess() {

        Map<String, Object> mockResponse = new HashMap<>();
        Map<String, Object> mockRates = new HashMap<>();
        mockRates.put("USD", 1.0);
        mockRates.put("EUR", 0.85);
        mockResponse.put("rates", mockRates);

        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(mockResponse);
        when(rateRepository.findByCurrency("USD")).thenReturn(null);
        when(rateRepository.findByCurrency("EUR")).thenReturn(null);

        rateService.fetchAndSaveRates();

        verify(rateRepository).save(argThat(rate -> "USD".equals(rate.getCurrency()) && 1.0 == rate.getRate()));
        verify(rateRepository).save(argThat(rate -> "EUR".equals(rate.getCurrency()) && 0.85 == rate.getRate()));
    }

    @Test
    void testFetchAndSaveRatesApiError() {

        when(restTemplate.getForObject(anyString(), eq(Map.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "API Error"));

        rateService.fetchAndSaveRates();

        verify(rateRepository, never()).save(any(RateEntity.class));
    }

    @Test
    void testFetchAndSaveRatesUnexpectedError() {

        when(restTemplate.getForObject(anyString(), eq(Map.class)))
                .thenThrow(new RuntimeException("Unexpected error"));

        rateService.fetchAndSaveRates();

        verify(rateRepository, never()).save(any(RateEntity.class));
    }
}
