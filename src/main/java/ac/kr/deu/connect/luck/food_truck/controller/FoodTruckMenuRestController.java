package ac.kr.deu.connect.luck.food_truck.controller;

import ac.kr.deu.connect.luck.exception.CustomErrorResponse;
import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckMenuRequest;
import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckMenuResponse;
import ac.kr.deu.connect.luck.food_truck.service.FoodTruckMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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

    @Operation(summary = "푸드트럭 메뉴 등록", description = "신규 푸드트럭 메뉴를 등록합니다. 푸드트럭 매니저의 역할이 필요합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "생성 완료", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = FoodTruckMenuResponse.class))}),
            @ApiResponse(responseCode = "403", description = "권한 없음", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CustomErrorResponse.class))}),
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_FOOD_TRUCK_MANAGER') and @checker.isFoodTruckManager(#foodTruckId)")
    public ResponseEntity<FoodTruckMenuResponse> saveFoodTruckMenu(
            @Parameter(description = "푸드트럭 UID") @PathVariable("foodTruckId") Long foodTruckId,
            @ModelAttribute FoodTruckMenuRequest foodTruckMenuRequest
    ) {
        FoodTruckMenuResponse foodTruckMenuResponse = foodTruckMenuService.saveFoodTruckMenu(foodTruckId, foodTruckMenuRequest);
        return ResponseEntity.created(URI.create("api/food-truck/" + foodTruckMenuResponse.id())).body(foodTruckMenuResponse);
    }


    @PatchMapping(value = "/{foodTruckMenuId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_FOOD_TRUCK_MANAGER') and @checker.isFoodTruckManager(#foodTruckId) and @checker.isFoodTruckMenu(#foodTruckId, #foodTruckMenuId)")
    @Operation(summary = "푸드트럭 메뉴 수정", description = "특정 푸드트럭 메뉴를 수정합니다. 푸드트럭 매니저의 역할이 필요합니다.")
    public ResponseEntity<FoodTruckMenuResponse> updateFoodTruckMenu(
            @Parameter(description = "푸드트럭 UID") @PathVariable("foodTruckId") Long foodTruckId,
            @Parameter(description = "메뉴 UID") @PathVariable("foodTruckMenuId") Long foodTruckMenuId,
            @ModelAttribute FoodTruckMenuRequest foodTruckMenuRequest
    ) {
        return ResponseEntity.ok(foodTruckMenuService.saveFoodTruckMenu(foodTruckId, foodTruckMenuRequest, foodTruckMenuId));
    }

    @Operation(summary = "푸드트럭 메뉴 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "성공", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Null.class))}),
            @ApiResponse(responseCode = "403", description = "권한 없음", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CustomErrorResponse.class))}),
    })
    @DeleteMapping("/{foodTruckMenuId}")
    @PreAuthorize("hasRole('ROLE_FOOD_TRUCK_MANAGER') and @checker.isFoodTruckManager(#foodTruckId) and @checker.isFoodTruckMenu(#foodTruckId, #foodTruckMenuId)")
    public ResponseEntity<String> deleteFoodTruckMenu(
            @Parameter(description = "푸드트럭 UID") @PathVariable("foodTruckId") Long foodTruckId,
            @Parameter(description = "메뉴 UID") @PathVariable("foodTruckMenuId") Long foodTruckMenuId
    ) {
        foodTruckMenuService.deleteFoodTruckMenu(foodTruckMenuId);
        return ResponseEntity.noContent().build();
    }
}
