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
    @Operation(summary = "푸드 트럭 검색")
    public ResponseEntity<List<FoodTruck>> getFoodTrucks(
            @Parameter(name = "상호명") @RequestParam(name = "name", required = false) String name,
            @Parameter(name = "음식 종류") @RequestParam(name = "foodType", required = false) FoodType foodType
    ) {
        return ResponseEntity.ok(foodTruckService.getFoodTrucks(name, foodType));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a food truck by id")
    public ResponseEntity<FoodTruckDetailResponse> getFoodTruck(
            @Parameter(name = "id", description = "트럭 ID를 조회") @PathVariable("id") Long id) {
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

