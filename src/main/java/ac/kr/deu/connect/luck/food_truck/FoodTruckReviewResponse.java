package ac.kr.deu.connect.luck.food_truck;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "리뷰 생성 요청 DTO")
public record FoodTruckReviewResponse(
        @Schema(description = "리뷰 ID", example = "1")
        Long id,
        @Schema(description = "리뷰 내용", example = "맛있어요")
        String content,
        @Schema(description = "이미지 URL", example = "https://picsum.photos/1600/900")
        String imageUrl,
        @Schema(description = "평점", example = "5")
        int score,
        @Schema(description = "푸드트럭 이름", example = "맛있는 푸드트럭")
        String foodTruckName,
        @Schema(description = "작성자 이름", example = "홍길동")
        String authorName,
        @Schema(description = "생성일", example = "2021-08-01T00:00:00")
        LocalDateTime createdAt,
        @Schema(description = "수정일", example = "2021-08-01T00:00:00")
        LocalDateTime updatedAt
) {
}
