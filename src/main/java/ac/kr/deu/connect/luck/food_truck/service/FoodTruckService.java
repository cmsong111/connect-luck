package ac.kr.deu.connect.luck.food_truck.service;

import ac.kr.deu.connect.luck.exception.CustomErrorCode;
import ac.kr.deu.connect.luck.exception.CustomException;
import ac.kr.deu.connect.luck.food_truck.FoodTruckMapper;
import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckDetailResponse;
import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckHeader;
import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckRequest;
import ac.kr.deu.connect.luck.food_truck.entity.FoodTruck;
import ac.kr.deu.connect.luck.food_truck.entity.FoodType;
import ac.kr.deu.connect.luck.food_truck.repository.FoodTruckMenuRepository;
import ac.kr.deu.connect.luck.food_truck.repository.FoodTruckRepository;
import ac.kr.deu.connect.luck.food_truck.repository.FoodTruckReviewRepository;
import ac.kr.deu.connect.luck.image.ImageUploader;
import ac.kr.deu.connect.luck.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    /**
     * 푸드트럭을 추가합니다.
     *
     * @param userEmail        푸드트럭 매니저의 ID
     * @param foodTruckRequest 추가할 푸드트럭 정보
     * @return 저장된 푸드트럭 정보
     */
    public FoodTruck createFoodTruck(String userEmail, FoodTruckRequest foodTruckRequest) {
        FoodTruck foodTruck = foodTruckMapper.toFoodTruck(foodTruckRequest);
        foodTruck.setManager(userRepository.findByEmail(userEmail).orElseThrow());
        foodTruck.setImageUrl("https://picsum.photos/1600/900");
        return foodTruckRepository.save(foodTruck);
    }

    /**
     * 푸드트럭을 삭제합니다.
     * 푸드트럭과 관련된 메뉴, 리뷰를 모두 삭제합니다.
     *
     * @param id 삭제할 푸드트럭의 ID
     */
    @Transactional
    public String deleteFoodTruck(Long id, String userEmail) {
        FoodTruck foodTruck = foodTruckRepository.findById(id).orElseThrow(
                () -> new CustomException(CustomErrorCode.FOOD_TRUCK_NOT_FOUND)
        );
        isManager(userEmail, foodTruck);
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
    public FoodTruckDetailResponse updateFoodTruck(Long foodTruckId, String userEmail, FoodTruckRequest foodTruckRequest) {
        // Search food truck
        FoodTruck foodTruck = foodTruckRepository.findById(foodTruckId).orElseThrow(
                () -> new CustomException(CustomErrorCode.FOOD_TRUCK_NOT_FOUND)
        );
        // Check if the user is the manager of the food truck
        isManager(userEmail, foodTruck);

        // 수정할 정보가 있는 경우 수정
        if (foodTruckRequest.name() != null) {
            foodTruck.setName(foodTruckRequest.name());
        }
        if (foodTruckRequest.description() != null) {
            foodTruck.setDescription(foodTruckRequest.description());
        }
        if (foodTruckRequest.foodType() != null) {
            foodTruck.setFoodType(foodTruckRequest.foodType());
        }
        // 수정된 푸드트럭 정보 저장
        FoodTruck saved = foodTruckRepository.save(foodTruck);
        return foodTruckMapper.toFoodTruckDetailResponse(saved);
    }

    /**
     * 푸드트럭의 대표 이미지를 변경합니다.
     *
     * @param foodTruckId   푸드트럭 ID
     * @param userEmail     사용자 Email
     * @param multipartFile 이미지 파일
     * @return 변경된 푸드트럭 정보
     */
    public FoodTruck updateFoodTruckImage(Long foodTruckId, String userEmail, MultipartFile multipartFile) {
        // Search food truck
        FoodTruck foodTruck = foodTruckRepository.findById(foodTruckId).orElseThrow(
                () -> new CustomException(CustomErrorCode.FOOD_TRUCK_NOT_FOUND)
        );

        // Check if the user is the manager of the food truck
        isManager(userEmail, foodTruck);

        // Upload image
        String imageUrl = imageUploader.uploadImage(multipartFile).getData().getUrl();

        foodTruck.setImageUrl(imageUrl);
        return foodTruckRepository.save(foodTruck);
    }

    /**
     * 푸드트럭 매니저인지 확인합니다.
     * 푸드트럭 매니저가 아닌 경우 예외를 발생시킵니다.
     * 푸드트럭 매니저이지만 해당 푸드트럭의 매니저가 아닌 경우 예외를 발생시킵니다.
     *
     * @param userEmail 사용자 Email
     * @param foodTruck 푸드트럭
     */
    protected void isManager(String userEmail, FoodTruck foodTruck) {
        if (!foodTruck.getManager().getEmail().equals(userEmail)) {
            throw new CustomException(CustomErrorCode.FOOD_TRUCK_IS_NOT_YOURS);
        }
    }
}
