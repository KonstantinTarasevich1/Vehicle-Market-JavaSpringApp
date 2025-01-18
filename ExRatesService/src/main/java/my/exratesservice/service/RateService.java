package my.exratesservice.service;

import my.exratesservice.model.entity.RateEntity;
import my.exratesservice.repository.RateRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import jakarta.annotation.PostConstruct;
import java.util.Map;

@Service
public class RateService {

    @Value("${openexchangerates.url}")
    private String apiUrl;

    @Value("${openexchangerates.app_id}")
    private String appId;

    private final RestTemplate restTemplate;
    private final RateRepository rateRepository;

    public RateService(RestTemplate restTemplate, RateRepository rateRepository) {
        this.restTemplate = restTemplate;
        this.rateRepository = rateRepository;
    }

    @PostConstruct
    public void fetchAndSaveRates() {
        System.out.println("API URL: " + apiUrl);
        System.out.println("App ID: " + appId);

        try {
            Map<String, Object> response = restTemplate.getForObject(apiUrl + "?app_id=" + appId, Map.class);
            Map<String, Object> rates = (Map<String, Object>) response.get("rates");

            rates.forEach((currency, rate) -> {
                Number rateNumber = (Number) rate;
                Double rateDouble = rateNumber.doubleValue();
                RateEntity existingRate = rateRepository.findByCurrency(currency);
                if (existingRate == null) {
                    RateEntity newRate = new RateEntity();
                    newRate.setCurrency(currency);
                    newRate.setRate(rateDouble);
                    rateRepository.save(newRate);
                } else {
                    existingRate.setRate(rateDouble);
                    rateRepository.save(existingRate);
                }
            });
        } catch (HttpClientErrorException e) {
            System.err.println("Error fetching rates from Open Exchange Rates API: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }

    public Iterable<RateEntity> getAllRates() {
        return rateRepository.findAll();
    }

    public RateEntity saveRate(RateEntity rate) {
        return rateRepository.save(rate);
    }

    public void deleteRate(Long id) {
        rateRepository.deleteById(id);
    }
}
