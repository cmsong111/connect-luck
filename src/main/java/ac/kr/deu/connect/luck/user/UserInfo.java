package ac.kr.deu.connect.luck.user;

import ac.kr.deu.connect.luck.food_truck.FoodTruckReview;

import java.util.List;

public record UserInfo(
        User user,
        List<FoodTruckReview> reviews
) {
}
