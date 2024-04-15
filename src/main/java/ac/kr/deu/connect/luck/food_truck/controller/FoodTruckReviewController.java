package ac.kr.deu.connect.luck.food_truck.controller;

import ac.kr.deu.connect.luck.food_truck.service.FoodTruckReviewService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class FoodTruckReviewController {

    private final FoodTruckReviewService foodTruckReviewService;
}
