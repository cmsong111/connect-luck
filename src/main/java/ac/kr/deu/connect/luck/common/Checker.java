package ac.kr.deu.connect.luck.common;

import ac.kr.deu.connect.luck.food_truck.entity.FoodTruck;
import ac.kr.deu.connect.luck.food_truck.entity.FoodTruckReview;
import ac.kr.deu.connect.luck.food_truck.repository.FoodTruckRepository;
import ac.kr.deu.connect.luck.food_truck.repository.FoodTruckReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Checker {
    private final FoodTruckRepository foodTruckRepository;
    private final FoodTruckReviewRepository foodTruckReviewRepository;

    /**
     * 요청자가 해당 푸드트럭의 매니저인지 확인
     *
     * @param id 푸드트럭 ID
     * @return 매니저이면 true, 아니면 false
     */
    public boolean isFoodTruckManager(Long id) {
        FoodTruck foodTruck = foodTruckRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 푸드트럭이 존재하지 않습니다.")
        );
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName().equals(foodTruck.getManager().getEmail());
    }


    /**
     * 해당 푸드트럭이 메뉴를 가지고 있는지 확인
     *
     * @param foodTruckId 푸드트럭 ID
     * @param menuId      메뉴 ID
     * @return 메뉴를 가지고 있으면 true, 아니면 false
     */
    public boolean isFoodTruckMenu(Long foodTruckId, Long menuId) {
        FoodTruck foodTruck = foodTruckRepository.findById(foodTruckId).orElseThrow(
                () -> new IllegalArgumentException("해당 푸드트럭이 존재하지 않습니다.")
        );
        return foodTruck.getMenus().stream().anyMatch(menu -> menu.getId().equals(menuId));
    }

    /**
     * 요청자가 해당 리뷰의 작성자인지 확인
     *
     * @param reviewId 리뷰 ID
     * @return 작성자이면 true, 아니면 false
     */
    public boolean isMyReview(Long reviewId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        FoodTruckReview foodTruckReview = foodTruckReviewRepository.findById(reviewId).orElseThrow(
                () -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다.")
        );
        return authentication.getName().equals(foodTruckReview.getAuthor().getEmail());
    }
}

