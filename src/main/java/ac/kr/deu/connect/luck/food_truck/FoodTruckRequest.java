package ac.kr.deu.connect.luck.food_truck;

public record FoodTruckRequest(
        String name,
        String description,
        String imageUrl,
        Long userId,
        FoodType foodType
) {
}
