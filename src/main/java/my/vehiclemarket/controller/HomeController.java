package my.vehiclemarket.controller;


import my.vehiclemarket.model.UserData;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@AuthenticationPrincipal UserData userData, Model model) {
        if (userData != null) {
            model.addAttribute("welcomeMessage", userData.getFullName());
        }
        else{
            model.addAttribute("welcomeMessage", "Anonymous");
        }

        return "index";
    }

    @GetMapping("/add-vehicle")
    public String showAddVehicleForm(Model model) {
        model.addAttribute("title", "Add Vehicle");
        return "add-vehicle";
    }
}
