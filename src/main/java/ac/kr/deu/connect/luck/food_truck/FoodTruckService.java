package ac.kr.deu.connect.luck.food_truck;

import ac.kr.deu.connect.luck.configuration.MapStructMapper;
import ac.kr.deu.connect.luck.exception.CustomErrorCode;
import ac.kr.deu.connect.luck.exception.CustomException;
import ac.kr.deu.connect.luck.user.User;
import ac.kr.deu.connect.luck.user.UserRepository;
import ac.kr.deu.connect.luck.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodTruckService {
    private final FoodTruckRepository foodTruckRepository;
    private final FoodTruckMenuRepository foodTruckMenuRepository;
    private final FoodTruckReviewRepository foodTruckReviewRepository;
    private final UserRepository userRepository;
    private final MapStructMapper mapStructMapper;

    /**
     * 모든 푸드트럭을 조회합니다.
     *
     * @return 모든 푸드트럭 정보
     */
    public List<FoodTruck> getFoodTrucks() {
        return foodTruckRepository.findAll();
    }

    /**
     * ID를 통해 특정 푸드트럭을 조회합니다.
     *
     * @param id 조회할 푸드트럭의 ID
     * @return 특정 푸드트럭 정보
     */
    public FoodTruck getFoodTruck(Long id) {
        return foodTruckRepository.findById(id).orElseThrow();
    }

    /**
     * 푸드트럭을 추가합니다.
     *
     * @param foodTruckRequest 추가할 푸드트럭 정보
     * @return 저장된 푸드트럭 정보
     */
    public FoodTruck saveFoodTruck(FoodTruckRequest foodTruckRequest) {
        FoodTruck foodTruck = mapStructMapper.toFoodTruck(foodTruckRequest);
        return foodTruckRepository.save(foodTruck);
    }

    /**
     * 푸드트럭을 삭제합니다.
     * 푸드트럭과 관련된 메뉴, 리뷰를 모두 삭제합니다.
     *
     * @param id 삭제할 푸드트럭의 ID
     */
    public void deleteFoodTruck(Long id) {
        FoodTruck foodTruck = foodTruckRepository.findById(id).orElseThrow(
                () -> new CustomException(CustomErrorCode.FOOD_TRUCK_NOT_FOUND)
        );
        foodTruckReviewRepository.deleteAllByFoodTruck(foodTruck);
        foodTruckMenuRepository.deleteAllByFoodTruck(foodTruck);
        foodTruckRepository.delete(foodTruck);
    }

    /**
     * 푸드트럭 정보를 수정합니다. 수정할 정보가 없는 경우 기존 정보를 유지합니다.
     *
     * @param id               푸드트럭 ID
     * @param foodTruckRequest 수정할 푸드트럭 정보
     * @return 수정된 푸드트럭 정보
     */
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

    /**
     * 푸드트럭 매니저가 되려는 사용자의 역할을 변경합니다.
     *
     * @param userId 푸드트럭 매니저가 되려는 사용자의 ID
     * @return 저장된 사용자 정보
     */
    public User becomeFoodTruckManager(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setRole(UserRole.FOOD_TRUCK_MANAGER);
        return userRepository.save(user);
    }
}
