package ac.kr.deu.connect.luck.food_truck;

import ac.kr.deu.connect.luck.configuration.MapStructMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodTruckService {
    private final FoodTruckRepository foodTruckRepository;
    private final FoodTruckMenuRepository foodTruckMenuRepository;
    private final FoodTruckReviewRepository foodTruckReviewRepository;
    private final MapStructMapper mapStructMapper;

    public List<FoodTruck> getFoodTrucks() {
        return foodTruckRepository.findAll();
    }

    public FoodTruck getFoodTruck(Long id) {
        return foodTruckRepository.findById(id).orElseThrow();
    }

    public FoodTruck saveFoodTruck(FoodTruckRequest foodTruckRequest) {
        FoodTruck foodTruck = mapStructMapper.toFoodTruck(foodTruckRequest);
        return foodTruckRepository.save(foodTruck);
    }

    public void deleteFoodTruck(Long id) {
        foodTruckRepository.deleteById(id);
    }

    public FoodTruck updateFoodTruck(Long id, FoodTruckRequest foodTruckRequest) {
        FoodTruck foodTruck = foodTruckRepository.findById(id).orElseThrow();
        if (foodTruckRequest.name() != null) {
            foodTruck.setName(foodTruckRequest.name());
        }
        if (foodTruckRequest.description() != null) {
            foodTruck.setDescription(foodTruckRequest.description());
        }
        if (foodTruckRequest.imageUrl() != null) {
            foodTruck.setImageUrl(foodTruckRequest.imageUrl());
        }
        if (foodTruckRequest.foodType() != null) {
            foodTruck.setFoodType(foodTruckRequest.foodType());
        }

        return foodTruckRepository.save(foodTruck);
    }
}
