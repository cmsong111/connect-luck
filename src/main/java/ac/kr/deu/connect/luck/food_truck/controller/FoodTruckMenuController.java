package ac.kr.deu.connect.luck.food_truck.controller;

import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckMenuRequest;
import ac.kr.deu.connect.luck.food_truck.service.FoodTruckMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/food-truck/menu")
@RequiredArgsConstructor
public class FoodTruckMenuController {

    private final FoodTruckMenuService foodTruckMenuService;

    @GetMapping("/{truckId}")
    @PreAuthorize("hasRole('ROLE_FOOD_TRUCK_MANAGER') and @checker.isFoodTruckManager(#truckId)")
    public String getFoodTruckMenuDetail(
            @PathVariable(value = "truckId") Long truckId, Model model
    ) {
        model.addAttribute("foodTruckMenus", foodTruckMenuService.getFoodTruckMenus(truckId));
        model.addAttribute("foodTruckId", truckId);
        model.addAttribute("foodTruckMenuRequest", new FoodTruckMenuRequest("", 0, "", null));
        return "food_truck/manager/food_truck_manage_menu";
    }

    @PostMapping(value = "/{truckId}/add", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ROLE_FOOD_TRUCK_MANAGER') and @checker.isFoodTruckManager(#truckId)")
    public String addFoodTruckMenu(
            @PathVariable(value = "truckId") Long truckId,
            FoodTruckMenuRequest foodTruckMenuRequest,
            Model model
    ) {
        foodTruckMenuService.saveFoodTruckMenu(truckId, foodTruckMenuRequest);
        model.addAttribute("foodTruckId", truckId);
        return "redirect:/food-truck/menu/" + truckId;
    }
}
