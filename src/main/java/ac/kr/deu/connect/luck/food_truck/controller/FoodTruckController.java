package ac.kr.deu.connect.luck.food_truck.controller;

import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckRequest;
import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckRequestV2;
import ac.kr.deu.connect.luck.food_truck.entity.FoodType;
import ac.kr.deu.connect.luck.food_truck.service.FoodTruckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

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

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasRole('ROLE_FOOD_TRUCK_MANAGER') and @checker.isFoodTruckManager(#id)")
    public String editFoodTruck(
            @PathVariable(value = "id") Long id,
            Model model
    ) {
        model.addAttribute("foodTruckOriginal", foodTruckService.getFoodTruck(id));
        model.addAttribute("foodTruck", foodTruckService.getFoodTruckForEdit(id));
        return "food_truck/manager/food_truck_edit";
    }

    @PostMapping(value = "/{id}/edit", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasRole('ROLE_FOOD_TRUCK_MANAGER') and @checker.isFoodTruckManager(#id)")
    public String editFoodTruckPost(
            @PathVariable Long id, Principal principal,
            FoodTruckRequestV2 foodTruckRequest
    ) {
        foodTruckService.saveFoodTruck(id, foodTruckRequest);
        return "redirect:/food-truck/my";
    }

    @PreAuthorize("hasRole('FOOD_TRUCK_MANAGER')")
    @GetMapping("/my")
    public String getMyFoodTruck(Model model, Principal principal) {
        model.addAttribute("foodTrucks", foodTruckService.getMyFoodTrucks(principal.getName()));
        model.addAttribute("foodTrucksRegisterForm", new FoodTruckRequestV2("", "", null, FoodType.KOREAN));
        return "food_truck/manager/my_food_truck_list";
    }


    @PreAuthorize("hasRole('FOOD_TRUCK_MANAGER')")
    @PostMapping("/register")
    public String registerFoodTruckPost(
            Principal principal,
            FoodTruckRequestV2 foodTruckRequest
    ) {
        foodTruckService.saveFoodTruck(principal.getName(), foodTruckRequest);
        return "redirect:/food-truck/my";
    }

    @GetMapping("/{id}/delete")
    @PreAuthorize("hasRole('ROLE_FOOD_TRUCK_MANAGER') and @checker.isFoodTruckManager(#id)")
    public String deleteFoodTruck(
            @PathVariable(value = "id") Long id
    ) {
        foodTruckService.deleteFoodTruck(id);
        return "redirect:/food-truck/my";
    }

}
