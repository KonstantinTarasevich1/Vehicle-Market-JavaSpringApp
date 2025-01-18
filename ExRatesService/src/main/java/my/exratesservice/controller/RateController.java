package my.exratesservice.controller;


import my.exratesservice.model.entity.RateEntity;
import my.exratesservice.service.RateService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rates")
public class RateController {

    private final RateService rateService;

    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    @GetMapping
    public Iterable<RateEntity> getAllRates() {
        return rateService.getAllRates();
    }

    @PostMapping
    public RateEntity saveRate(@RequestBody RateEntity rate) {
        return rateService.saveRate(rate);
    }

    @DeleteMapping("/{id}")
    public void deleteRate(@PathVariable Long id) {
        rateService.deleteRate(id);
    }
}
