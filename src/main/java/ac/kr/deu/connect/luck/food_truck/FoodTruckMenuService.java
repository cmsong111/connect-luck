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
public class FoodTruckMenuService {

    private final FoodTruckMenuRepository foodTruckMenuRepository;
    private final FoodTruckRepository foodTruckRepository;
    private final UserRepository userRepository;
    private final MapStructMapper mapStructMapper;

    List<FoodTruckMenu> getFoodTruckMenus(Long foodTruckId) {
        return foodTruckMenuRepository.findByFoodTruckId(foodTruckId);
    }

    FoodTruckMenu saveFoodTruckMenu(Long userId, Long foodTruckId, FoodTruckMenuRequest foodTruckMenuRequest) {
        isManager(userId, foodTruckId);
        FoodTruckMenu foodTruckMenu = mapStructMapper.toFoodTruckMenu(foodTruckMenuRequest);
        foodTruckMenu.setFoodTruck(foodTruckRepository.findById(foodTruckId).orElseThrow());
        return foodTruckMenuRepository.save(foodTruckMenu);
    }

    FoodTruckMenu updateFoodTruckMenu(Long userId, Long foodTruckId, Long foodTruckMenuId, FoodTruckMenuRequest foodTruckMenuRequest) {
        isManager(userId, foodTruckId);
        FoodTruckMenu foodTruckMenu = foodTruckMenuRepository.findById(foodTruckMenuId).orElseThrow();
        foodTruckMenu.setName(foodTruckMenuRequest.name());
        foodTruckMenu.setPrice(foodTruckMenuRequest.price());
        foodTruckMenu.setImageUrl(foodTruckMenuRequest.imageUrl());
        foodTruckMenu.setDescription(foodTruckMenuRequest.description());
        return foodTruckMenuRepository.save(foodTruckMenu);
    }

    void deleteFoodTruckMenu(Long userId, Long foodTruckId, Long foodTruckMenuId) {
        isManager(userId, foodTruckId);
        if (!foodTruckMenuRepository.existsById(foodTruckMenuId)) {
            throw new CustomException(CustomErrorCode.FOOD_TRUCK_MENU_NOT_FOUND);
        }
        foodTruckMenuRepository.deleteById(foodTruckMenuId);
    }

    protected void isManager(Long userId, Long foodTruckId) {
        FoodTruck foodTruck = foodTruckRepository.findById(foodTruckId).orElseThrow(
                () -> new CustomException(CustomErrorCode.FOOD_TRUCK_NOT_FOUND)
        );
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(CustomErrorCode.USER_ID_NOT_MATCH)
        );
        if (user.getRole() != UserRole.FOOD_TRUCK_MANAGER) {
            throw new CustomException(CustomErrorCode.ROLE_NOT_MATCH);
        }
        if (!foodTruck.getManager().equals(user)) {
            throw new CustomException(CustomErrorCode.FOOD_TRUCK_IS_NOT_YOURS);
        }
    }
}