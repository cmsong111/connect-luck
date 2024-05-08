package ac.kr.deu.connect.luck.food_truck.controller;

import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckDetailResponse;
import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckHeader;
import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckRequest;
import ac.kr.deu.connect.luck.food_truck.entity.FoodTruck;
import ac.kr.deu.connect.luck.food_truck.entity.FoodType;
import ac.kr.deu.connect.luck.food_truck.service.FoodTruckService;
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

@Tag(name = "03-Food Truck", description = "Food Truck API")
@RestController
@RequestMapping("/api/food-truck")
@RequiredArgsConstructor
public class FoodTruckRestController {
    private final FoodTruckService foodTruckService;

    @GetMapping
    @Operation(summary = "푸드 트럭 검색", description = "푸드트럭을 검색합니다.")
    public ResponseEntity<List<FoodTruckHeader>> getFoodTrucks(
            @Parameter(description = "상호명") @RequestParam(name = "name", required = false) String name,
            @Parameter(description = "음식 종류") @RequestParam(name = "foodType", required = false) FoodType foodType
    ) {
        return ResponseEntity.ok(foodTruckService.getFoodTrucks(name, foodType));
    }

    @GetMapping("/{id}")
    @Operation(summary = "푸드트럭 정보를 조회합니다.")
    public ResponseEntity<FoodTruckDetailResponse> getFoodTruck(
            @Parameter(description = "푸드트럭 UID") @PathVariable("id") Long id) {
        return ResponseEntity.ok(foodTruckService.getFoodTruck(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_FOOD_TRUCK_MANAGER')")
    @Operation(summary = "푸드트럭 등록", description = "<h1>신규 푸드트럭을 등록합니다.</h1><b>푸드트럭 관리자만 가능합니다.</b><h3>이후 추가해야 하는 일</h3><ul><li>푸드트럭 메뉴 등록</li><li>푸드트럭 Now API를 통한 영업 위치 등록(등록 후 바로 삭제)</li><li>대표이미지 변경(아래 {id}/update-image API 활용</li></ul>")
    public ResponseEntity<FoodTruck> createFoodTruck(
            @RequestBody FoodTruckRequest foodTruckRequest,
            Principal principal) {
        return ResponseEntity.ok(foodTruckService.createFoodTruck(principal.getName(), foodTruckRequest));
    }


    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_FOOD_TRUCK_MANAGER') and @checker.isFoodTruckManager(#truckId)")
    @Operation(summary = "푸드트럭 정보를 업데이트 합니다.", description = "푸드트럭 정보를 업데이트 합니다. 푸드트럭 관리자만 가능합니다.<br>푸드트럭의 대표 이미지를 변경하려면 /{id}/update-image API를 활용하십시오.")
    public ResponseEntity<FoodTruckDetailResponse> updateFoodTruck(
            @Parameter(description = "푸드트럭 UID") @PathVariable("id") Long truckId,
            @RequestBody FoodTruckRequest foodTruckRequest,
            Principal principal) {
        return ResponseEntity.ok(foodTruckService.updateFoodTruck(truckId, principal.getName(), foodTruckRequest));
    }

    @PostMapping(value = "/{id}/update-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_FOOD_TRUCK_MANAGER')")
    @Operation(summary = "푸드트럭의 대표 이미지를 변경합니다.", description = "Image를 업로드하여 푸드트럭의 대표 이미지를 변경합니다.")
    public ResponseEntity<FoodTruck> updateFoodTruckImage(
            @Parameter(description = "푸드트럭 UID") @PathVariable("id") Long truckId,
            @Parameter(description = "변경할 이미지") @RequestPart("image") MultipartFile multipartFile,
            Principal principal) {
        return ResponseEntity.ok(foodTruckService.updateFoodTruckImage(truckId, principal.getName(), multipartFile));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_FOOD_TRUCK_MANAGER')")
    @Operation(summary = "푸드트럭 정보 삭제", description = "<h1>푸드트럭 정보를 삭제합니다.</h1><h3>삭제 되는 정보</h3><ol><li>푸드트럭 정보</li><li>푸드트럭 메뉴</li><li>푸드트럭 리뷰</li></ol>")
    public ResponseEntity<String> deleteFoodTruck(
            Principal principal,
            @PathVariable Long id) {
        return ResponseEntity.ok(foodTruckService.deleteFoodTruck(id, principal.getName()));
    }
}

