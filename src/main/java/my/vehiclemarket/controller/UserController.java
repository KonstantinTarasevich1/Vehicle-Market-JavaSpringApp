package my.vehiclemarket.controller;

import my.vehiclemarket.model.dto.BoatSummaryDTO;
import my.vehiclemarket.model.dto.CarSummaryDTO;
import my.vehiclemarket.model.dto.MotorcycleSummaryDTO;
import my.vehiclemarket.model.dto.TruckSummaryDTO;
import my.vehiclemarket.service.impl.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;
    private final CarServiceImpl carService;
    private final MotorcycleServiceImpl motorcycleService;
    private final BoatServiceImpl boatService;
    private final TruckServiceImpl truckService;

    public UserController(UserServiceImpl userService, CarServiceImpl carService, MotorcycleServiceImpl motorcycleService, BoatServiceImpl boatService, TruckServiceImpl truckService) {
        this.userService = userService;
        this.carService = carService;
        this.motorcycleService = motorcycleService;
        this.boatService = boatService;
        this.truckService = truckService;
    }

    @GetMapping("/profile")
    public String getProfile(Model model) {
        List<CarSummaryDTO> cars = carService.getCarsForCurrentUser();
        List<BoatSummaryDTO> boats = boatService.getBoatsForCurrentUser();
        List<MotorcycleSummaryDTO> motorcycles = motorcycleService.getMotorcyclesForCurrentUser();
        List<TruckSummaryDTO> trucks = truckService.getTrucksForCurrentUser();

        model.addAttribute("cars", cars);
        model.addAttribute("boats", boats);
        model.addAttribute("motorcycles", motorcycles);
        model.addAttribute("trucks", trucks);

        return "profile";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);

        return "redirect:/";
    }
}
