package ac.kr.deu.connect.luck.food_truck.service;

import ac.kr.deu.connect.luck.exception.CustomErrorCode;
import ac.kr.deu.connect.luck.exception.CustomException;
import ac.kr.deu.connect.luck.food_truck.FoodTruckMapper;
import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckDetailResponse;
import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckHeader;
import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckRequestV2;
import ac.kr.deu.connect.luck.food_truck.entity.FoodTruck;
import ac.kr.deu.connect.luck.food_truck.entity.FoodType;
import ac.kr.deu.connect.luck.food_truck.repository.FoodTruckMenuRepository;
import ac.kr.deu.connect.luck.food_truck.repository.FoodTruckRepository;
import ac.kr.deu.connect.luck.food_truck.repository.FoodTruckReviewRepository;
import ac.kr.deu.connect.luck.image.ImageUploader;
import ac.kr.deu.connect.luck.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoodTruckService {
    private final FoodTruckRepository foodTruckRepository;
    private final FoodTruckMenuRepository foodTruckMenuRepository;
    private final FoodTruckReviewRepository foodTruckReviewRepository;
    private final UserRepository userRepository;
    private final FoodTruckMapper foodTruckMapper;
    private final ImageUploader imageUploader;

    /**
     * 모든 푸드트럭을 조회합니다.
     *
     * @return 모든 푸드트럭 정보
     */
    public List<FoodTruckHeader> getFoodTrucks(String name, FoodType foodType) {
        List<FoodTruck> foodTrucks;
        if (name == null && foodType == null) {
            foodTrucks = foodTruckRepository.findAll();
        } else if (name != null && foodType == null) {
            foodTrucks = foodTruckRepository.findByNameContaining(name);
        } else if (name == null) {
            foodTrucks = foodTruckRepository.findByFoodType(foodType);
        } else {
            foodTrucks = foodTruckRepository.findByNameContainingAndFoodType(name, foodType);
        }
        return foodTrucks.stream().map(foodTruckMapper::toFoodTruckHeader).toList();
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

        return foodTruckMapper.toFoodTruckDetailResponse(foodTruck);
    }

    public FoodTruckRequestV2 getFoodTruckForEdit(Long id) {
        FoodTruck foodTruck = foodTruckRepository.findById(id).orElseThrow(
                () -> new CustomException(CustomErrorCode.FOOD_TRUCK_NOT_FOUND)
        );
        return foodTruckMapper.toFoodTruckRequest(foodTruck);
    }

    /**
     * 푸드트럭을 추가합니다.
     *
     * @param userEmail        푸드트럭 매니저의 ID
     * @param foodTruckRequest 추가할 푸드트럭 정보
     * @return 저장된 푸드트럭 정보
     */
    public FoodTruckDetailResponse saveFoodTruck(String userEmail, FoodTruckRequestV2 foodTruckRequest) {
        FoodTruck foodTruck = foodTruckMapper.toFoodTruck(foodTruckRequest);
        foodTruck.setManager(userRepository.findByEmail(userEmail));

        // 이미지가 있는 경우 이미지 업로드
        if (foodTruckRequest.getImage() != null) {
            foodTruck.setImageUrl(imageUploader.uploadImage(foodTruckRequest.getImage()).getData().getUrl());
        }

        return foodTruckMapper.toFoodTruckDetailResponse(foodTruckRepository.save(foodTruck));
    }

    /**
     * 푸드트럭을 삭제합니다.
     * 푸드트럭과 관련된 메뉴, 리뷰를 모두 삭제합니다.
     *
     * @param id 삭제할 푸드트럭의 ID
     */
    @Transactional
    public String deleteFoodTruck(Long id) {
        FoodTruck foodTruck = foodTruckRepository.findById(id).orElseThrow(
                () -> new CustomException(CustomErrorCode.FOOD_TRUCK_NOT_FOUND)
        );
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
    public FoodTruckDetailResponse saveFoodTruck(Long foodTruckId, FoodTruckRequestV2 foodTruckRequest) {
        // Search food truck
        FoodTruck foodTruck = foodTruckRepository.findById(foodTruckId).orElseThrow(
                () -> new CustomException(CustomErrorCode.FOOD_TRUCK_NOT_FOUND)
        );

        foodTruck.setName(foodTruckRequest.getName());
        foodTruck.setDescription(foodTruckRequest.getDescription());
        foodTruck.setFoodType(foodTruckRequest.getFoodType());

        try {
            if (foodTruckRequest.getImage() != null) {
                foodTruck.setImageUrl(imageUploader.uploadImage(foodTruckRequest.getImage()).getData().getUrl());
            }
        } catch (Exception e) {
            log.error("Failed to upload image", e);
        }

        // 수정된 푸드트럭 정보 저장
        FoodTruck saved = foodTruckRepository.save(foodTruck);
        return foodTruckMapper.toFoodTruckDetailResponse(saved);
    }


    public List<FoodTruck> getMyFoodTrucks(String userEmail) {
        return foodTruckRepository.findAllByManagerEmail(userEmail);
    }

    public List<FoodTruckDetailResponse> getMyFoodTrucksForApi(String userEmail) {
        return foodTruckRepository.findAllByManagerEmail(userEmail).stream()
                .map(foodTruckMapper::toFoodTruckDetailResponse)
                .toList();
    }
}
