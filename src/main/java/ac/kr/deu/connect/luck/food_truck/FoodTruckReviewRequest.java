package ac.kr.deu.connect.luck.food_truck;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "리뷰 생성 요청 DTO")
public record FoodTruckReviewRequest(
        @Schema(description = "리뷰 내용", example = "맛있어요")
        String content,
        @Schema(description = "이미지 URL", example = "https://picsum.photos/1600/900")
        String imageUrl,
        @Schema(description = "평점", example = "5")
        int score,
        @Schema(description = "푸드트럭 ID", example = "1")
        Long foodTruckId,
        @Schema(description = "작성자 ID", example = "1")
        Long authorId
) {
}
