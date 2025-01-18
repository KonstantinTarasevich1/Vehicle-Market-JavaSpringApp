package my.vehiclemarket.controller;

import jakarta.validation.Valid;
import my.vehiclemarket.service.impl.TruckServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import my.vehiclemarket.model.dto.TruckEntityDTO;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/trucks")
public class TruckController {

    private final TruckServiceImpl truckService;

    public TruckController(TruckServiceImpl truckService) {
        this.truckService = truckService;
    }

    @ModelAttribute("truckData")
    public TruckEntityDTO truckDTO() {
        return new TruckEntityDTO();
    }

    @GetMapping()
    public String showTruckPage(Model model) {
        model.addAttribute("allTrucks", truckService.getAllTrucksSummary());
        return "trucks";
    }

    @GetMapping("/{id}")
    public String motorcycleDetails(@PathVariable("id") Long id, Model model) {
        model.addAttribute("truckDetails", truckService.getTruckDetails(id));

        return "truck-details";
    }

    @GetMapping("/add-truck")
    public String addTruckForm() {
        return "add-truck";
    }

    @PostMapping("/add-truck")
    public String addTruck(
            @Valid TruckEntityDTO data,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("truckData", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.carData", bindingResult);

            return "redirect:/trucks/add-truck";
        }

        boolean success = truckService.save(data);

        if (!success) {
            redirectAttributes.addFlashAttribute("truckData", data);
            return "redirect:/trucks/add-truck";
        }
        return "redirect:/";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteTruck(@PathVariable Long id) {
        truckService.delete(id);
        return "redirect:/trucks";
    }
}
