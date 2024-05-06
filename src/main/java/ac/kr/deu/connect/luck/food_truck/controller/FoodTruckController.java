package ac.kr.deu.connect.luck.food_truck.controller;

import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckRequest;
import ac.kr.deu.connect.luck.food_truck.entity.FoodType;
import ac.kr.deu.connect.luck.food_truck.service.FoodTruckService;
import lombok.RequiredArgsConstructor;
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
    @PreAuthorize("hasRole('FOOD_TRUCK_MANAGER')")
    public String editFoodTruck(
            @PathVariable Long id, Model model
    ) {
        model.addAttribute("foodTruck", foodTruckService.getFoodTruck(id));
        return "food_truck/manager/food_truck_edit";
    }

    @PostMapping("/{id}/edit")
    @PreAuthorize("hasRole('FOOD_TRUCK_MANAGER')")
    public String editFoodTruckPost(
            @PathVariable Long id, Principal principal,
            FoodTruckRequest foodTruckRequest
    ) {
        foodTruckService.updateFoodTruck(id, principal.getName(), foodTruckRequest);
        return "redirect:/food-truck/my";
    }

    @GetMapping("{id}/edit.photo")
    @PreAuthorize("hasRole('FOOD_TRUCK_MANAGER')")
    public String editFoodTruckPhoto(
            @PathVariable Long id, Model model
    ) {
        model.addAttribute("foodTruck", foodTruckService.getFoodTruck(id));
        return "food_truck/manager/food_truck_edit_photo";
    }

    @PostMapping(value = "{id}/edit.photo", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('FOOD_TRUCK_MANAGER')")
    public String editFoodTruckPhotoPost(
            @PathVariable Long id, Principal principal,
            MultipartFile image
    ) {
        foodTruckService.updateFoodTruckImage(id, principal.getName(), image);
        return "redirect:/food-truck/{id}/edit";
    }


    @PreAuthorize("hasRole('FOOD_TRUCK_MANAGER')")
    @GetMapping("/my")
    public String getMyFoodTruck(Model model, Principal principal) {
        model.addAttribute("foodTrucks", foodTruckService.getMyFoodTrucks(principal.getName()));
        return "food_truck/manager/my_food_truck_list";
    }


    @PreAuthorize("hasRole('FOOD_TRUCK_MANAGER')")
    @GetMapping("/register")
    public String registerFoodTruck() {
        return "food_truck/manager/food_truck_register";
    }

    @PreAuthorize("hasRole('FOOD_TRUCK_MANAGER')")
    @PostMapping("/register")
    public String registerFoodTruckPost(
            Principal principal,
            String name,
            String description,
            String foodType,
            MultipartFile image
    ) {
        FoodType type = FoodType.valueOf(foodType);
        FoodTruckRequest foodTruckRequest = new FoodTruckRequest(name, description, type);
        foodTruckService.createFoodTruck(principal.getName(), foodTruckRequest);
        return "redirect:/food-truck/my";
    }

}
