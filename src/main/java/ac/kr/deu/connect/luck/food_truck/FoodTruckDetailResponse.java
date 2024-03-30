package ac.kr.deu.connect.luck.food_truck;

import ac.kr.deu.connect.luck.user.User;
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
        @Schema(description = "음식 종류", example = "KOREAN")
        FoodType foodType,
        @Schema(description = "푸드트럭 관리자")
        User manager,
        @Schema(description = "푸드트럭 리뷰 목록")
        List<FoodTruckReviewResponse> reviews,
        @Schema(description = "푸드트럭 메뉴 목록")
        List<FoodTruckMenuResponse> menus
) {
}
