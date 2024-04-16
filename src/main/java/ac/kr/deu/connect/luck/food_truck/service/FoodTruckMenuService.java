package ac.kr.deu.connect.luck.food_truck.service;

import ac.kr.deu.connect.luck.exception.CustomErrorCode;
import ac.kr.deu.connect.luck.exception.CustomException;
import ac.kr.deu.connect.luck.food_truck.FoodTruckMapper;
import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckMenuResponse;
import ac.kr.deu.connect.luck.food_truck.entity.FoodTruck;
import ac.kr.deu.connect.luck.food_truck.entity.FoodTruckMenu;
import ac.kr.deu.connect.luck.food_truck.repository.FoodTruckMenuRepository;
import ac.kr.deu.connect.luck.food_truck.repository.FoodTruckRepository;
import ac.kr.deu.connect.luck.image.ImageUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodTruckMenuService {

    private final FoodTruckMenuRepository foodTruckMenuRepository;
    private final FoodTruckRepository foodTruckRepository;
    private final FoodTruckMapper mapStructMapper;
    private final ImageUploader imageUploader;


    /**
     * 푸드트럭의 메뉴 리스트 조회
     *
     * @param foodTruckId 푸드트럭 ID
     * @return 푸드트럭 메뉴 리스트
     */
    public List<FoodTruckMenuResponse> getFoodTruckMenus(Long foodTruckId) {
        return foodTruckMenuRepository.findByFoodTruckId(foodTruckId).stream()
                .map(mapStructMapper::toFoodTruckMenuResponse)
                .collect(Collectors.toList());
    }

    /**
     * 푸드트럭 메뉴 등록
     *
     * @param foodTruckId   푸드트럭 ID
     * @param userEmail     푸드트럭 매니저 이메일
     * @param name          메뉴 이름
     * @param description   메뉴 설명
     * @param price         메뉴 가격
     * @param multipartFile 메뉴 이미지
     * @return FoodTruckMenu
     */
    public FoodTruckMenu saveFoodTruckMenu(Long foodTruckId, String userEmail, String name, String description, int price, MultipartFile multipartFile) {
        FoodTruck foodTruck = foodTruckRepository.findById(foodTruckId).orElseThrow(
                () -> new CustomException(CustomErrorCode.FOOD_TRUCK_NOT_FOUND)
        );
        isManager(userEmail, foodTruck);

        String imageUrl = imageUploader.uploadImage(multipartFile).getData().getImage().getUrl();

        FoodTruckMenu foodTruckMenu = FoodTruckMenu.builder()
                .name(name)
                .description(description)
                .price(price)
                .imageUrl(imageUrl)
                .foodTruck(foodTruck)
                .build();

        return foodTruckMenuRepository.save(foodTruckMenu);
    }

    public FoodTruckMenu updateFoodTruckMenu(Long foodTruckId, Long foodTruckMenuId, String userEmail, String name, String description, int price, MultipartFile multipartFile) {
        FoodTruck foodTruck = foodTruckRepository.findById(foodTruckId).orElseThrow();
        FoodTruckMenu foodTruckMenu = foodTruckMenuRepository.findById(foodTruckMenuId).orElseThrow();
        isManager(userEmail, foodTruck);

        if (foodTruckMenu.getFoodTruck().getId() != foodTruckId) {
            throw new CustomException(CustomErrorCode.FOOD_TRUCK_IS_NOT_YOURS);
        }

        if (name != null) {
            foodTruckMenu.setName(name);
        }

        if (description != null) {
            foodTruckMenu.setDescription(description);
        }

        if (price != 0) {
            foodTruckMenu.setPrice(price);
        }

        if (multipartFile != null) {
            String imageUrl = imageUploader.uploadImage(multipartFile).getData().getImage().getUrl();
            foodTruckMenu.setImageUrl(imageUrl);
        }

        return foodTruckMenuRepository.save(foodTruckMenu);
    }

    public void deleteFoodTruckMenu(String userEmail, Long foodTruckId, Long foodTruckMenuId) {
        FoodTruck foodTruck = foodTruckRepository.findById(foodTruckId).orElseThrow();
        FoodTruckMenu foodTruckMenu = foodTruckMenuRepository.findById(foodTruckMenuId).orElseThrow();
        isManager(userEmail, foodTruck);

        // 메뉴가 푸드트럭의 메뉴인지 확인
        if (foodTruckMenu.getFoodTruck().getId() != foodTruckId) {
            throw new CustomException(CustomErrorCode.FOOD_TRUCK_IS_NOT_YOURS);
        }

        foodTruckMenuRepository.deleteById(foodTruckMenuId);
    }

    /**
     * 푸드트럭 매니저인지 확인
     *
     * @param email     매니저 이메일
     * @param foodTruck 푸드트럭
     */
    protected void isManager(String email, FoodTruck foodTruck) {
        if (!foodTruck.getManager().getEmail().equals(email)) {
            throw new CustomException(CustomErrorCode.FOOD_TRUCK_IS_NOT_YOURS);
        }
    }
}
