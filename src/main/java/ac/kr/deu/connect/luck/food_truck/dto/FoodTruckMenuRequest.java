package ac.kr.deu.connect.luck.food_truck.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record FoodTruckMenuRequest(
        @Schema(description = "메뉴 이름", example = "맛있는 푸드트럭")
        String name,
        @Schema(description = "메뉴 설명", example = "맛있는 음식")
        String description,
        @Schema(description = "메뉴 사진 URL", example = "https://picsum.photos/1600/900")
        String imageUrl,
        @Schema(description = "가격", example = "10000")
        int price
) {
}
