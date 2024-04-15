package ac.kr.deu.connect.luck.food_truck.service;

import ac.kr.deu.connect.luck.configuration.MapStructMapper;
import ac.kr.deu.connect.luck.exception.CustomErrorCode;
import ac.kr.deu.connect.luck.exception.CustomException;
import ac.kr.deu.connect.luck.food_truck.entity.FoodTruck;
import ac.kr.deu.connect.luck.food_truck.entity.FoodType;
import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckDetailResponse;
import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckRequest;
import ac.kr.deu.connect.luck.food_truck.entity.FoodTruckMenu;
import ac.kr.deu.connect.luck.food_truck.entity.FoodTruckReview;
import ac.kr.deu.connect.luck.food_truck.repository.FoodTruckMenuRepository;
import ac.kr.deu.connect.luck.food_truck.repository.FoodTruckRepository;
import ac.kr.deu.connect.luck.food_truck.repository.FoodTruckReviewRepository;
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
    public List<FoodTruck> getFoodTrucks(String name, FoodType foodType) {
        if (name == null && foodType == null) {
            return foodTruckRepository.findAll();
        } else if (name != null && foodType == null) {
            return foodTruckRepository.findByNameContaining(name);
        } else if (name == null) {
            return foodTruckRepository.findByFoodType(foodType);
        } else {
            return foodTruckRepository.findByNameContainingAndFoodType(name, foodType);
        }
    }

    /**
     * ID를 통해 특정 푸드트럭을 조회합니다.
     *
     * @param id 조회할 푸드트럭의 ID
     * @return 특정 푸드트럭 정보
     */
    public FoodTruckDetailResponse getFoodTruck(Long id) {
        FoodTruck foodTruck = foodTruckRepository.findById(id).orElseThrow(
                () -> new CustomException(CustomErrorCode.FOOD_TRUCK_NOT_FOUND)
        );
        List<FoodTruckMenu> foodTruckMenus = foodTruckMenuRepository.findByFoodTruckId(id);
        List<FoodTruckReview> foodTruckReviews = foodTruckReviewRepository.findByFoodTruckId(id);
        return mapStructMapper.toFoodTruckDetailResponse(foodTruck, foodTruckReviews, foodTruckMenus);

    }

    /**
     * 푸드트럭을 추가합니다.
     *
     * @param foodTruckRequest 추가할 푸드트럭 정보
     * @return 저장된 푸드트럭 정보
     */
    public FoodTruck saveFoodTruck(Long userId, FoodTruckRequest foodTruckRequest) {
        isManager(userId);
        FoodTruck foodTruck = mapStructMapper.toFoodTruck(foodTruckRequest);
        foodTruck.setManager(userRepository.findById(userId).orElseThrow());
        return foodTruckRepository.save(foodTruck);
    }

    /**
     * 푸드트럭을 삭제합니다.
     * 푸드트럭과 관련된 메뉴, 리뷰를 모두 삭제합니다.
     *
     * @param id 삭제할 푸드트럭의 ID
     */
    public String deleteFoodTruck(Long id) {
        FoodTruck foodTruck = foodTruckRepository.findById(id).orElseThrow(
                () -> new CustomException(CustomErrorCode.FOOD_TRUCK_NOT_FOUND)
        );
        foodTruckReviewRepository.deleteAllByFoodTruck(foodTruck);
        foodTruckMenuRepository.deleteAllByFoodTruck(foodTruck);
        foodTruckRepository.delete(foodTruck);
        return "삭제되었습니다.";
    }

    /**
     * 푸드트럭 정보를 수정합니다. 수정할 정보가 없는 경우 기존 정보를 유지합니다.
     *
     * @param foodTruckId      푸드트럭 ID
     * @param foodTruckRequest 수정할 푸드트럭 정보
     * @return 수정된 푸드트럭 정보
     */
    public FoodTruck updateFoodTruck(Long foodTruckId, Long userId, FoodTruckRequest foodTruckRequest) {
        // Search food truck
        FoodTruck foodTruck = foodTruckRepository.findById(foodTruckId).orElseThrow(
                () -> new CustomException(CustomErrorCode.FOOD_TRUCK_NOT_FOUND)
        );
        // Check if the user is the manager of the food truck
        isManager(userId, foodTruck);

        // 수정할 정보가 있는 경우 수정
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

        // 수정된 푸드트럭 정보 저장
        return foodTruckRepository.save(foodTruck);
    }

    /**
     * 푸드트럭 매니저인지 확인합니다.
     * 푸드트럭 매니저가 아닌 경우 예외를 발생시킵니다.
     * 푸드트럭 매니저이지만 해당 푸드트럭의 매니저가 아닌 경우 예외를 발생시킵니다.
     *
     * @param userId    사용자 ID
     * @param foodTruck 푸드트럭
     */
    protected void isManager(Long userId, FoodTruck foodTruck) {
        isManager(userId);
        if (!foodTruck.getManager().getId().equals(userId)) {
            throw new CustomException(CustomErrorCode.FOOD_TRUCK_IS_NOT_YOURS);
        }
    }

    protected void isManager(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(CustomErrorCode.USER_ID_NOT_MATCH)
        );
        if (!user.getRole().equals(UserRole.FOOD_TRUCK_MANAGER)) {
            throw new CustomException(CustomErrorCode.ROLE_NOT_MATCH);
        }
    }


}
