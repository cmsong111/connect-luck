package ac.kr.deu.connect.luck.food_truck.dto;

import ac.kr.deu.connect.luck.food_truck.entity.FoodType;

public record FoodTruckHeader(
        Long id,
        String name,
        String description,
        String imageUrl,
        String managerName,
        FoodType foodType,
        int reviewCount,
        double avgRating
) {
}
