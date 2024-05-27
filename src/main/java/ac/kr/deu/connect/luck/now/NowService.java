package ac.kr.deu.connect.luck.now;

import ac.kr.deu.connect.luck.exception.CustomErrorCode;
import ac.kr.deu.connect.luck.exception.CustomException;
import ac.kr.deu.connect.luck.food_truck.entity.FoodTruck;
import ac.kr.deu.connect.luck.food_truck.repository.FoodTruckRepository;
import ac.kr.deu.connect.luck.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NowService {
    protected static Double DISTANCE = 0.05;
    private final NowRepository nowRepository;
    private final FoodTruckRepository foodTruckRepository;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 현재 운영중인 푸드트럭 조회
     *
     * @param latitude  위도
     * @param longitude 경도
     * @return 현재 운영중인 푸드트럭 정보
     */
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

    /**
     * 푸드트럭 운영 시작
     *
     * @param foodTruckId 푸드트럭 ID
     * @param req         요청 정보(JWT 토큰)
     * @param latitude    위도
     * @param longitude   경도
     * @return 현재 운영중인 푸드트럭 정보
     */
    public Now saveStartNow(Long foodTruckId, HttpServletRequest req, Double latitude, Double longitude) {
        // 정보 확인
        String userEmail = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req));
        FoodTruck foodTruck = foodTruckRepository.findById(foodTruckId).orElseThrow(
                () -> new CustomException(CustomErrorCode.FOOD_TRUCK_NOT_FOUND)
        );
        isManager(userEmail, foodTruck);

        // 이미 운영중인지 확인
        Now now = nowRepository.findByFoodTruckId(foodTruckId).orElse(
                Now.builder()
                        .foodTruck(FoodTruck.builder().id(foodTruckId).build())
                        .build()
        );
        // 운영 좌표 저장
        now.setLatitude(latitude);
        now.setLongitude(longitude);
        now.setIsOperating(true);

        // 저장 후 반환
        return nowRepository.save(now);
    }

    /**
     * 푸드트럭 운영 종료
     *
     * @param foodTruckId 푸드트럭 ID
     * @param req         요청 정보(JWT 토큰)
     * @return 현재 운영중인 푸드트럭 정보
     */
    public Now saveEndNow(Long foodTruckId, HttpServletRequest req) {
        // 정보 확인
        String userEmail = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req));
        FoodTruck foodTruck = foodTruckRepository.findById(foodTruckId).orElseThrow(
                () -> new CustomException(CustomErrorCode.FOOD_TRUCK_NOT_FOUND)
        );
        isManager(userEmail, foodTruck);

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
     * 본인의 푸드트럭인지 확인
     *
     * @param userEmail 사용자 이메일
     * @param foodTruck 푸드트럭
     */
    protected void isManager(String userEmail, FoodTruck foodTruck) {
        if (!foodTruck.getManager().getEmail().equals(userEmail)) {
            throw new CustomException(CustomErrorCode.FOOD_TRUCK_IS_NOT_YOURS);
        }
    }
}
