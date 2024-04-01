package ac.kr.deu.connect.luck.now;

import ac.kr.deu.connect.luck.exception.CustomErrorCode;
import ac.kr.deu.connect.luck.exception.CustomException;
import ac.kr.deu.connect.luck.food_truck.FoodTruck;
import ac.kr.deu.connect.luck.food_truck.FoodTruckRepository;
import ac.kr.deu.connect.luck.user.User;
import ac.kr.deu.connect.luck.user.UserRepository;
import ac.kr.deu.connect.luck.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NowService {
    private final NowRepository nowRepository;
    private final FoodTruckRepository foodTruckRepository;
    private final UserRepository userRepository;


    protected static Double DISTANCE = 0.05;


    public List<Now> getNow(
            Double latitude,
            Double longitude
    ) {
        if (latitude != null && longitude != null) {
            return nowRepository.findByLatitudeBetweenAndLongitudeBetweenAndIsOperating(
                    latitude - DISTANCE, latitude + DISTANCE,
                    longitude - DISTANCE, longitude + DISTANCE,
                    true
            );
        } else {
            return nowRepository.findByIsOperating(true);
        }
    }

    public Now saveStartNow(Long foodTruckId, Long userId, Double latitude, Double longitude) {
        isManager(userId, foodTruckId);

        Now now = nowRepository.findByFoodTruckId(foodTruckId).orElse(
                Now.builder()
                        .foodTruck(FoodTruck.builder().id(foodTruckId).build())
                        .build()
        );

        now.setLatitude(latitude);
        now.setLongitude(longitude);
        now.setIsOperating(true);

        return nowRepository.save(now);
    }

    public Now saveEndNow(Long foodTruckId, Long userId) {
        isManager(userId, foodTruckId);
        Now now = nowRepository.findByFoodTruckId(foodTruckId).orElseThrow(
                () -> new CustomException(CustomErrorCode.FOOD_TRUCK_IS_NOT_OPERATING)
        );
        if (!now.getIsOperating()) {
            throw new CustomException(CustomErrorCode.FOOD_TRUCK_IS_NOT_OPERATING);
        }
        now.setIsOperating(false);
        return nowRepository.save(now);
    }

    /**
     * Check if the user is the manager of the food truck
     *
     * @param userId      user id
     * @param foodTruckId food truck id
     */
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
        if (foodTruck.getManager().getId() != userId) {
            throw new CustomException(CustomErrorCode.FOOD_TRUCK_IS_NOT_YOURS);
        }
    }
}
