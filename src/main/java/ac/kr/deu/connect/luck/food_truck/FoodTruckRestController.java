package ac.kr.deu.connect.luck.food_truck;

import io.swagger.v3.oas.annotations.Operation;
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


}
