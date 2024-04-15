package ac.kr.deu.connect.luck.food_truck.service;

import ac.kr.deu.connect.luck.food_truck.entity.FoodTruckReview;
import ac.kr.deu.connect.luck.food_truck.repository.FoodTruckReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodTruckReviewService {

    private final FoodTruckReviewRepository foodTruckReviewRepository;

    public List<FoodTruckReview> getFoodTruckReviews(Long foodTruckId) {
        return foodTruckReviewRepository.findByFoodTruckId(foodTruckId);
    }
}
