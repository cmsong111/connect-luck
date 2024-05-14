package ac.kr.deu.connect.luck.common;

import ac.kr.deu.connect.luck.food_truck.entity.FoodTruck;
import ac.kr.deu.connect.luck.food_truck.repository.FoodTruckRepository;
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

    public boolean isFoodTruckManager(Long id) {
        FoodTruck foodTruck = foodTruckRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 푸드트럭이 존재하지 않습니다.")
        );
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName().equals(foodTruck.getManager().getEmail());
    }
}

