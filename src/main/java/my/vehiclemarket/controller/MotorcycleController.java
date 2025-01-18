package my.vehiclemarket.controller;

import jakarta.validation.Valid;
import my.vehiclemarket.service.impl.MotorcycleServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import my.vehiclemarket.model.dto.MotorcycleEntityDTO;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/motorcycles")
public class MotorcycleController {

    private final MotorcycleServiceImpl motorcycleService;

    public MotorcycleController(MotorcycleServiceImpl motorcycleService) {
        this.motorcycleService = motorcycleService;
    }

    @ModelAttribute("motorcycleData")
    public MotorcycleEntityDTO motorcycleDTO() {
        return new MotorcycleEntityDTO();
    }

    @GetMapping()
    public String showMotorcyclePage(Model model) {
        model.addAttribute("allMotorcycles", motorcycleService.getAllMotorcyclesSummary());
        return "motorcycles";
    }

    @GetMapping("/{id}")
    public String motorcycleDetails(@PathVariable("id") Long id, Model model) {
        model.addAttribute("motorcycleDetails", motorcycleService.getMotorcycleDetails(id));

        return "motorcycle-details";
    }

    @GetMapping("/add-motorcycle")
    public String addMotorcycleForm() {
        return "add-motorcycle";
    }

    @PostMapping("/add-motorcycle")
    public String addMotorcycle(
            @Valid MotorcycleEntityDTO data,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("motorcycleData", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.carData", bindingResult);

            return "redirect:/motorcycles/add-motorcycle";
        }

        boolean success = motorcycleService.save(data);

        if (!success) {
            redirectAttributes.addFlashAttribute("motorcycleData", data);
            return "redirect:/motorcycles/add-motorcycle";
        }
        return "redirect:/";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteMotorcycle(@PathVariable Long id) {
        motorcycleService.delete(id);
        return "redirect:/motorcycles";
    }
}
