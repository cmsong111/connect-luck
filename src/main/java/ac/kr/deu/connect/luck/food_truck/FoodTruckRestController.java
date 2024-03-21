package ac.kr.deu.connect.luck.food_truck;

import ac.kr.deu.connect.luck.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Food Truck", description = "Food Truck API")
@RequestMapping("/api/food-truck")
@RequiredArgsConstructor
public class FoodTruckRestController {
    private final FoodTruckService foodTruckService;

    @PostMapping
    @Operation(summary = "Create a food truck")
    public ResponseEntity<FoodTruck> createFoodTruck(
            @RequestBody FoodTruckRequest foodTruckRequest) {
        return ResponseEntity.ok(foodTruckService.saveFoodTruck(foodTruckRequest));
    }

    @GetMapping
    @Operation(summary = "Get all food trucks")
    public ResponseEntity<List<FoodTruck>> getFoodTrucks() {
        return ResponseEntity.ok(foodTruckService.getFoodTrucks());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a food truck by id")
    public ResponseEntity<FoodTruck> getFoodTruck(
            @PathVariable Long id) {
        return ResponseEntity.ok(foodTruckService.getFoodTruck(id));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update a food truck by id")
    public ResponseEntity<FoodTruck> updateFoodTruck(
            @PathVariable Long id,
            @RequestBody FoodTruckRequest foodTruckRequest) {
        return ResponseEntity.ok(foodTruckService.updateFoodTruck(id, foodTruckRequest));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a food truck by id")
    public ResponseEntity<Void> deleteFoodTruck(
            @PathVariable Long id) {
        foodTruckService.deleteFoodTruck(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/be-manager")
    @Operation(summary = "become a manager of a food truck")
    public ResponseEntity<User> becomeManager(
            @Parameter(name = "id", description = "유저 ID") @RequestParam("id") Long id) {
        return ResponseEntity.ok(foodTruckService.becomeFoodTruckManager(id));
    }

}
