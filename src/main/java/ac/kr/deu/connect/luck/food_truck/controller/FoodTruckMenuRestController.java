package ac.kr.deu.connect.luck.food_truck.controller;

import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckMenuResponse;
import ac.kr.deu.connect.luck.food_truck.entity.FoodTruckMenu;
import ac.kr.deu.connect.luck.food_truck.service.FoodTruckMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Tag(name = "04-Food Truck Menu API", description = "푸드트럭 메뉴 API")
@RestController
@RequestMapping("/api/food-truck/{foodTruckId}/menu")
@RequiredArgsConstructor
public class FoodTruckMenuRestController {

    private final FoodTruckMenuService foodTruckMenuService;

    @GetMapping
    @Operation(summary = "푸드트럭의 메뉴 조회", description = "특정 푸드트럭 메뉴를 조회합니다.")
    public ResponseEntity<List<FoodTruckMenuResponse>> getFoodTruckMenus(
            @Parameter(description = "푸드트럭 UID") @PathVariable("foodTruckId") Long foodTruckId
    ) {
        return ResponseEntity.ok(foodTruckMenuService.getFoodTruckMenus(foodTruckId));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_FOOD_TRUCK_MANAGER')")
    @Operation(summary = "푸드트럭 메뉴 등록", description = "신규 푸드트럭 메뉴를 등록합니다. 푸드트럭 매니저의 역할이 필요합니다.")
    public ResponseEntity<FoodTruckMenu> saveFoodTruckMenu(
            @Parameter(description = "푸드트럭 UID") @PathVariable("foodTruckId") Long foodTruckId,
            @Parameter(description = "메뉴 이름") @RequestPart("name") String name,
            @Parameter(description = "메뉴 설명") @RequestPart("description") String description,
            @Parameter(description = "메뉴 가격(숫자만 넣어주세요)") @RequestPart("price") String price,
            @Parameter(description = "메뉴 이미지") @RequestPart("image") MultipartFile multipartFile,
            Principal principal
    ) {
        return ResponseEntity.ok(foodTruckMenuService.saveFoodTruckMenu(foodTruckId, principal.getName(), name, description, Integer.parseInt(price), multipartFile));
    }


    @PatchMapping(value = "/{foodTruckMenuId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_FOOD_TRUCK_MANAGER')")
    @Operation(summary = "푸드트럭 메뉴 수정")
    public ResponseEntity<FoodTruckMenu> updateFoodTruckMenu(
            @Parameter(description = "푸드트럭 UID") @PathVariable("foodTruckId") Long foodTruckId,
            @Parameter(description = "메뉴 UID") @PathVariable("foodTruckMenuId") Long foodTruckMenuId,
            @Parameter(description = "메뉴 이름") @RequestPart(value = "name", required = false) String name,
            @Parameter(description = "메뉴 설명") @RequestPart(value = "description", required = false) String description,
            @Parameter(description = "메뉴 가격(숫자만 넣어주세요)") @RequestPart(value = "price", required = false) String price,
            @Parameter(description = "메뉴 이미지") @RequestPart(value = "image", required = false) MultipartFile multipartFile,
            Principal principal
    ) {
        return ResponseEntity.ok(foodTruckMenuService.updateFoodTruckMenu(foodTruckId, foodTruckMenuId, principal.getName(), name, description, Integer.parseInt(price), multipartFile));
    }

    @DeleteMapping("/{foodTruckMenuId}")
    @PreAuthorize("hasRole('ROLE_FOOD_TRUCK_MANAGER')")
    @Operation(summary = "푸드트럭 메뉴 삭제")
    public ResponseEntity<String> deleteFoodTruckMenu(
            @Parameter(description = "푸드트럭 UID") @PathVariable("foodTruckId") Long foodTruckId,
            @Parameter(description = "메뉴 UID") @PathVariable("foodTruckMenuId") Long foodTruckMenuId,
            Principal principal
    ) {
        foodTruckMenuService.deleteFoodTruckMenu(principal.getName(), foodTruckId, foodTruckMenuId);
        return ResponseEntity.ok("success");
    }
}
