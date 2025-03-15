package ac.kr.deu.connect.luck.foodtruck.controller.response;

import ac.kr.deu.connect.luck.foodtruck.entity.FoodType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record FoodTruckDetailResponse(
        @Schema(description = "푸드트럭 ID", example = "1")
        Long id,
        @Schema(description = "푸드트럭 이름", example = "맛있는 푸드트럭")
        String name,
        @Schema(description = "푸드트럭 설명", example = "맛있는 음식")
        String description,
        @Schema(description = "푸드트럭 이미지 URL", example = "https://picsum.photos/1600/900")
        String imageUrl,
        @Schema(description = "푸드트럭 관리자 이름", example = "홍길동")
        String managerName,
        @Schema(description = "음식 종류", example = "KOREAN")
        FoodType foodType,
        @Schema(description = "푸드트럭 리뷰 목록")
        List<FoodTruckReviewResponse> reviews,
        @Schema(description = "푸드트럭 메뉴 목록")
        List<FoodTruckMenuResponse> menus,
        @Schema(description = "리뷰 평균 점수", example = "4.5")
        Double avgRating
) {
}
