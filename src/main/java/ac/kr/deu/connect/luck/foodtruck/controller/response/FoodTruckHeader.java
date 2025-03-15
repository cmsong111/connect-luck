package ac.kr.deu.connect.luck.foodtruck.controller.response;

import ac.kr.deu.connect.luck.foodtruck.entity.FoodType;

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
