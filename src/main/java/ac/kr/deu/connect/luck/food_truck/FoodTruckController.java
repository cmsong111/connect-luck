package ac.kr.deu.connect.luck.food_truck;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/food-truck")
@RequiredArgsConstructor
public class FoodTruckController {
    private final FoodTruckService foodTruckService;

    @GetMapping
    public String getFoodTruck(Model model) {
        model.addAttribute("foodTrucks", foodTruckService.getFoodTrucks(null, null));
        return "food_truck/food_truck_list";
    }

    @GetMapping("/{id}")
    public String getFoodTruckDetail(
            @PathVariable Long id, Model model
    ) {
        model.addAttribute("foodTruck", foodTruckService.getFoodTruck(id));
        return "food_truck/food_truck_detail";
    }


    @GetMapping("/register")
    public String registerFoodTruck() {
        return "food_truck/food_truck_register";
    }

}
