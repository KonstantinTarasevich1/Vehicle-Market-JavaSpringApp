package my.vehiclemarket.controller;

import jakarta.validation.Valid;
import my.vehiclemarket.service.impl.CarServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import my.vehiclemarket.model.dto.CarEntityDTO;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cars")
public class CarController {

    private final CarServiceImpl carService;

    public CarController(CarServiceImpl carService) {
        this.carService = carService;
    }

    @ModelAttribute("carData")
    public CarEntityDTO carDTO() {
        return new CarEntityDTO();
    }

    @GetMapping()
    public String showCarsPage(Model model) {
        model.addAttribute("allCars", carService.getAllCarsSummary());
        return "cars";
    }

    @GetMapping("/{id}")
    public String carDetails(@PathVariable("id") Long id, Model model) {
        model.addAttribute("carDetails", carService.getCarDetails(id));

        return "car-details";
    }

    @GetMapping("/add-car")
    public String addCarForm() {
        return "add-car";
    }

    @PostMapping("/add-car")
    public String addCar(
            @Valid CarEntityDTO data,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("carData", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.carData", bindingResult);

            return "redirect:/cars/add-car";
        }

        boolean success = carService.save(data);

        if (!success) {
            redirectAttributes.addFlashAttribute("carData", data);
            return "redirect:/cars/add-car";
        }
        return "redirect:/";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCar(@PathVariable Long id) {
        carService.delete(id);
        return "redirect:/cars";
    }
}

