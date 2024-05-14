package ac.kr.deu.connect.luck.food_truck.service;

import ac.kr.deu.connect.luck.food_truck.FoodTruckMapper;
import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckMenuRequest;
import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckMenuResponse;
import ac.kr.deu.connect.luck.food_truck.entity.FoodTruck;
import ac.kr.deu.connect.luck.food_truck.entity.FoodTruckMenu;
import ac.kr.deu.connect.luck.food_truck.repository.FoodTruckMenuRepository;
import ac.kr.deu.connect.luck.food_truck.repository.FoodTruckRepository;
import ac.kr.deu.connect.luck.image.ImageUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodTruckMenuService {

    private final FoodTruckMenuRepository foodTruckMenuRepository;
    private final FoodTruckRepository foodTruckRepository;
    private final FoodTruckMapper mapStructMapper;
    private final ImageUploader imageUploader;
    private final FoodTruckMapper foodTruckMapper;


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
     * <p>컨트룰러에서 검증을 마치고 요청되는 메소드 이므로 믿고 사용할 수 있다.</p>
     *
     * @param foodTruckId          푸드트럭 ID
     * @param foodTruckMenuRequest 푸드트럭 메뉴 요청
     * @return FoodTruckMenu
     */
    public FoodTruckMenuResponse saveFoodTruckMenu(Long foodTruckId, FoodTruckMenuRequest foodTruckMenuRequest) {
        // 객체 생성
        FoodTruck foodTruck = FoodTruck.builder().id(foodTruckId).build();
        FoodTruckMenu foodTruckMenu = foodTruckMapper.toFoodTruckMenu(foodTruckMenuRequest);

        // 푸드트럭 메뉴에 푸드트럭 설정
        foodTruckMenu.setFoodTruck(foodTruck);

        // 이미지가 있으면 업로드
        if (foodTruckMenuRequest.getImage() != null) {
            String imageUrl = imageUploader.uploadImage(foodTruckMenuRequest.getImage()).getData().getImage().getUrl();
            foodTruckMenu.setImageUrl(imageUrl);
        }

        // 저장 후 응답
        return foodTruckMapper.toFoodTruckMenuResponse(foodTruckMenuRepository.save(foodTruckMenu));
    }

    /**
     * 푸드트럭 메뉴 수정
     *
     * @param foodTruckId          푸드트럭 ID
     * @param foodTruckMenuRequest 푸드트럭 메뉴 요청
     * @param foodTruckMenuId      푸드트럭 메뉴 ID
     * @return FoodTruckMenu 응답
     */
    public FoodTruckMenuResponse saveFoodTruckMenu(Long foodTruckId, FoodTruckMenuRequest foodTruckMenuRequest, Long foodTruckMenuId) {
        // 객체 생성
        FoodTruckMenu foodTruckMenu = foodTruckMenuRepository.findById(foodTruckMenuId)
                .orElseThrow(RuntimeException::new);

        // 푸드트럭 메뉴 ID 설정
        foodTruckMenu.setName(foodTruckMenuRequest.getName());
        foodTruckMenu.setDescription(foodTruckMenuRequest.getDescription());
        foodTruckMenu.setPrice(foodTruckMenuRequest.getPrice());


        // 이미지가 있으면 업로드
        if (foodTruckMenuRequest.getImage() != null) {
            String imageUrl = imageUploader.uploadImage(foodTruckMenuRequest.getImage()).getData().getImage().getUrl();
            foodTruckMenu.setImageUrl(imageUrl);
        }

        // 저장 후 응답
        return foodTruckMapper.toFoodTruckMenuResponse(foodTruckMenuRepository.save(foodTruckMenu));
    }


    public void deleteFoodTruckMenu(Long foodTruckMenuId) {
        foodTruckMenuRepository.deleteById(foodTruckMenuId);
    }
}
