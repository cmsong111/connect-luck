package ac.kr.deu.connect.luck.food_truck.dto;

import ac.kr.deu.connect.luck.food_truck.entity.FoodType;
import io.swagger.v3.oas.annotations.media.Schema;

public record FoodTruckRequest(
        @Schema(description = "푸드트럭 이름", example = "맛있는 푸드트럭")
        String name,
        @Schema(description = "푸드트럭 설명", example = "맛있는 음식")
        String description,
        @Schema(description = "음식 종류", example = "KOREAN")
        FoodType foodType
) {
}
