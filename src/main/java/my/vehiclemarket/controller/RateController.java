package my.vehiclemarket.controller;

import my.vehiclemarket.model.entity.RateEntity;
import my.vehiclemarket.service.impl.RateService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RateController {

    private final RateService rateService;

    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    @GetMapping("/rates")
    public String getRates(Model model) {
        List<RateEntity> rates = rateService.getAllRates();
        model.addAttribute("rates", rates);
        return "rates";
    }

    @PostMapping("/rates")
    public String saveRate(RateEntity rate) {
        rateService.saveRate(rate);
        return "redirect:/rates";
    }

    @DeleteMapping("/rates/{id}")
    public String deleteRate(@PathVariable Long id) {
        rateService.deleteRate(id);
        return "redirect:/rates";
    }
}
