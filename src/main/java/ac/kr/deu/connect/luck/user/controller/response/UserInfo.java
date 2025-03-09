package ac.kr.deu.connect.luck.user.controller.response;

import ac.kr.deu.connect.luck.food_truck.entity.FoodTruckReview;

import ac.kr.deu.connect.luck.user.entity.User;
import java.util.List;

public record UserInfo(
        User user,
        List<FoodTruckReview> reviews
) {
}
