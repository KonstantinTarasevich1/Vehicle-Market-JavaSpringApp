package my.vehiclemarket.controller;

import jakarta.validation.Valid;
import my.vehiclemarket.service.impl.BoatServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import my.vehiclemarket.model.dto.BoatEntityDTO;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/boats")
public class BoatController {

    private final BoatServiceImpl boatService;

    public BoatController(BoatServiceImpl boatService) {
        this.boatService = boatService;
    }

    @ModelAttribute("boatData")
    public BoatEntityDTO boatDTO() {
        return new BoatEntityDTO();
    }

    @GetMapping()
    public String showBoatsPage(Model model) {
        model.addAttribute("allBoats", boatService.getAllBoatsSummary());
        return "boats";
    }

    @GetMapping("/{id}")
    public String boatDetails(@PathVariable("id") Long id, Model model) {
        model.addAttribute("boatDetails", boatService.getBoatDetails(id));

        return "boat-details";
    }

    @GetMapping("/add-boat")
    public String addBoatForm() {
        return "add-boat";
    }

    @PostMapping("/add-boat")
    public String addBoat(
            @Valid BoatEntityDTO data,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("boatData", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.carData", bindingResult);

            return "redirect:/boats/add-boat";
        }

        boolean success = boatService.save(data);

        if (!success) {
            redirectAttributes.addFlashAttribute("boatData", data);
            return "redirect:/boats/add-boat";
        }
        return "redirect:/";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBoat(@PathVariable Long id) {
        boatService.delete(id);
        return "redirect:/boats";
    }
}
