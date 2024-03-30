package ac.kr.deu.connect.luck.food_truck;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Food Truck Menu API", description = "푸드트럭 메뉴 API")
@RestController
@RequestMapping("/api/food-truck/{foodTruckId}/menu")
@RequiredArgsConstructor
public class FoodTruckMenuRestController {

    private final FoodTruckMenuService foodTruckMenuService;

    @PostMapping
    @Operation(summary = "푸드트럭 메뉴 등록")
    public ResponseEntity<FoodTruckMenu> saveFoodTruckMenu(
            @PathVariable("foodTruckId") Long foodTruckId,
            @RequestHeader("USER_ID") Long userId,
            @RequestBody FoodTruckMenuRequest foodTruckMenuRequest
    ) {
        return ResponseEntity.ok(foodTruckMenuService.saveFoodTruckMenu(userId, foodTruckId, foodTruckMenuRequest));
    }


    @PatchMapping("/{foodTruckMenuId}")
    @Operation(summary = "푸드트럭 메뉴 수정")
    public ResponseEntity<FoodTruckMenu> updateFoodTruckMenu(
            @PathVariable("foodTruckId") Long foodTruckId,
            @PathVariable("foodTruckMenuId") Long foodTruckMenuId,
            @RequestHeader("USER_ID") Long userId,
            @RequestBody FoodTruckMenuRequest foodTruckMenuRequest
    ) {
        return ResponseEntity.ok(foodTruckMenuService.updateFoodTruckMenu(userId, foodTruckId, foodTruckMenuId, foodTruckMenuRequest));
    }

    @DeleteMapping("/{foodTruckMenuId}")
    @Operation(summary = "푸드트럭 메뉴 삭제")
    public ResponseEntity<String> deleteFoodTruckMenu(
            @PathVariable("foodTruckId") Long foodTruckId,
            @PathVariable("foodTruckMenuId") Long foodTruckMenuId,
            @RequestHeader("USER_ID") Long userId
    ) {
        foodTruckMenuService.deleteFoodTruckMenu(userId, foodTruckId, foodTruckMenuId);
        return ResponseEntity.ok("success");
    }


}
