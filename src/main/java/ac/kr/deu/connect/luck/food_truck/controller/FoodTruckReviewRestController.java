package ac.kr.deu.connect.luck.food_truck.controller;

import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckReviewRequest;
import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckReviewResponse;
import ac.kr.deu.connect.luck.food_truck.service.FoodTruckReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.security.Principal;

@Tag(name = "05-Food Truck Review", description = "Food Truck Review API")
@RestController
@RequestMapping("/api/food-truck/{foodTruckId}/review")
@AllArgsConstructor
public class FoodTruckReviewRestController {

    private final FoodTruckReviewService foodTruckReviewService;

    @Operation(summary = "푸드트럭 리뷰 등록", description = "푸드트럭 리뷰를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "리뷰 등록 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FoodTruckReviewResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    })
    @PreAuthorize("isAuthenticated()")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FoodTruckReviewResponse> createFoodTruckReview(
            @Parameter(description = "푸드트럭 ID") @PathVariable("foodTruckId") Long foodTruckId,
            @ModelAttribute @Valid FoodTruckReviewRequest foodTruckReviewRequest,
            Principal principal
    ) {
        FoodTruckReviewResponse foodTruckReviewResponse = foodTruckReviewService.saveFoodTruckReview(foodTruckId, foodTruckReviewRequest, principal.getName());
        return ResponseEntity.created(URI.create("api/food-truck/" + foodTruckId + "/review/")).body(foodTruckReviewResponse);
    }

    @Operation(summary = "푸드트럭 리뷰 수정", description = "푸드트럭 리뷰를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "리뷰 등록 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FoodTruckReviewResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    })
    @PreAuthorize("@checker.isMyReview(#reviewId)")
    @PatchMapping(value = "/{reviewId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FoodTruckReviewResponse> updateFoodTruckReview(
            @Parameter(description = "푸드트럭 ID") @PathVariable("foodTruckId") Long foodTruckId,
            @Parameter(description = "리뷰 ID") @PathVariable("reviewId") Long reviewId,
            @ModelAttribute @Valid FoodTruckReviewRequest foodTruckReviewRequest,
            Principal principal
    ) {
        FoodTruckReviewResponse foodTruckReviewResponse = foodTruckReviewService.saveFoodTruckReview(foodTruckId, foodTruckReviewRequest, principal.getName(), reviewId);
        return ResponseEntity.ok(foodTruckReviewResponse);
    }

    @Operation(summary = "푸드트럭 리뷰 삭제", description = "작성자 또는 푸드트럭 매니저의 권한이 필요합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "리뷰 삭제 성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    })
    @DeleteMapping("/{reviewId}")
    @PreAuthorize("@checker.isMyReview(#reviewId) or @checker.isFoodTruckManager(#foodTruckId)")
    public ResponseEntity<String> deleteFoodTruckReview(
            @Parameter(description = "푸드트럭 ID") @PathVariable("foodTruckId") Long foodTruckId,
            @Parameter(description = "리뷰 ID") @PathVariable("reviewId") Long reviewId
    ) {
        foodTruckReviewService.deleteFoodTruckReview(reviewId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "푸드트럭 리뷰 답글 등록", description = "푸드트럭 리뷰에 답글을 등록합니다.<br>푸드트럭 매니저의 권한이 필요합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "리뷰 답글 등록 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FoodTruckReviewResponse.class))),
            @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/{reviewId}/reply")
    @PreAuthorize("@checker.isFoodTruckManager(#foodTruckId)")
    public ResponseEntity<FoodTruckReviewResponse> createFoodTruckReviewReply(
            @Parameter(description = "푸드트럭 ID") @PathVariable("foodTruckId") Long foodTruckId,
            @Parameter(description = "리뷰 ID") @PathVariable("reviewId") Long reviewId,
            @Parameter(description = "답글 내용") @RequestParam String content
    ) {
        FoodTruckReviewResponse foodTruckReviewResponse = foodTruckReviewService.addReplyToReview(reviewId, content);
        return ResponseEntity.created(URI.create("api/food-truck/" + foodTruckId + "/review")).body(foodTruckReviewResponse);
    }
}
