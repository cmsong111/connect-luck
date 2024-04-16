package ac.kr.deu.connect.luck.food_truck.controller;

import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckReviewResponse;
import ac.kr.deu.connect.luck.food_truck.service.FoodTruckReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Slf4j
@Tag(name = "05-Food Truck Review", description = "Food Truck Review API")
@RestController
@RequestMapping("/api/food-truck/review")
@AllArgsConstructor
public class FoodTruckReviewRestController {

    private final FoodTruckReviewService foodTruckReviewService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "푸드트럭 리뷰 등록", description = "푸드트럭 리뷰를 등록합니다.")
    public ResponseEntity<FoodTruckReviewResponse> createFoodTruckReview(
            @Parameter(description = "푸드트럭 ID (숫자만 넣어주세요)") @RequestPart("food-truck_id") String id,
            @Parameter(description = "리뷰 내용") @RequestPart(name = "content") String content,
            @Parameter(description = "평점 (숫자만 넣어주세요)") @RequestPart(name = "score") String score,
            @Parameter(description = "multipart/form-data") @RequestPart(value = "image", required = false) MultipartFile multipartFile,
            Principal principal
    ) {
        return ResponseEntity.ok(foodTruckReviewService.createFoodTruckReview(Long.parseLong(id), principal.getName(), content, Integer.parseInt(score), multipartFile));
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "푸드트럭 리뷰 수정", description = "푸드트럭 리뷰를 수정합니다.")
    public ResponseEntity<FoodTruckReviewResponse> updateFoodTruckReview(
            @Parameter(description = "리뷰 ID") @PathVariable("id") Long id,
            @Parameter(description = "리뷰 내용") @RequestPart(name = "content", required = false) String content,
            @Parameter(description = "평점") @RequestPart(name = "score", required = false) String score,
            @Parameter(description = "multipart/form-data") @RequestPart(value = "image", required = false) MultipartFile multipartFile,
            Principal principal
    ) {
        return ResponseEntity.ok(foodTruckReviewService.updateFoodTruckReview(id, principal.getName(), content, Integer.parseInt(score), multipartFile));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "푸드트럭 리뷰 삭제", description = "푸드트럭 리뷰를 삭제합니다.")
    public ResponseEntity<String> deleteFoodTruckReview(
            @Parameter(description = "리뷰 ID") @PathVariable("id") Long id,
            Principal principal
    ) {
        foodTruckReviewService.deleteFoodTruckReview(id, principal.getName());
        return ResponseEntity.ok("리뷰가 삭제되었습니다.");
    }

    @PostMapping("/{id}/reply")
    @Operation(summary = "푸드트럭 리뷰 답글 등록", description = "푸드트럭 리뷰에 답글을 등록합니다.<br>푸드트럭 매니저의 권한이 필요합니다.")
    @PreAuthorize("hasRole('ROLE_FOOD_TRUCK_MANAGER')")
    public ResponseEntity<FoodTruckReviewResponse> createFoodTruckReviewReply(
            @Parameter(description = "리뷰 ID") @PathVariable("id") Long id,
            @Parameter(description = "답글 내용") @RequestParam String content,
            Principal principal
    ) {
        return ResponseEntity.ok(foodTruckReviewService.createFoodTruckReviewReply(id, principal.getName(), content));
    }
}
