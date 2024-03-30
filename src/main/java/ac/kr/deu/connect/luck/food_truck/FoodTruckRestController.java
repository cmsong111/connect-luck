package ac.kr.deu.connect.luck.food_truck;

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
            @RequestHeader("USER_ID") Long userId,
            @RequestBody FoodTruckRequest foodTruckRequest) {
        return ResponseEntity.ok(foodTruckService.saveFoodTruck(userId, foodTruckRequest));
    }

    @GetMapping
    @Operation(summary = "Get all food trucks")
    public ResponseEntity<List<FoodTruck>> getFoodTrucks() {
        return ResponseEntity.ok(foodTruckService.getFoodTrucks());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a food truck by id")
    public ResponseEntity<FoodTruck> getFoodTruck(
            @Parameter(name = "id", description = "트럭 ID를 조회") @PathVariable Long id) {
        return ResponseEntity.ok(foodTruckService.getFoodTruck(id));
    }

    @PatchMapping("/{foodTruck-id}")
    @Operation(summary = "Update a food truck by id")
    public ResponseEntity<FoodTruck> updateFoodTruck(
            @PathVariable("foodTruck-id") Long truckId,
            @Parameter(name = "유저 ID") @RequestHeader("USER_ID") Long userId,
            @RequestBody FoodTruckRequest foodTruckRequest) {
        return ResponseEntity.ok(foodTruckService.updateFoodTruck(truckId, userId, foodTruckRequest));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a food truck by id")
    public ResponseEntity<String> deleteFoodTruck(
            @RequestHeader("USER_ID") Long userId,
            @PathVariable Long id) {
        return ResponseEntity.ok(foodTruckService.deleteFoodTruck(id));
    }
}
