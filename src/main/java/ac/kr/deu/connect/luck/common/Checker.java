package ac.kr.deu.connect.luck.common;

import ac.kr.deu.connect.luck.food_truck.entity.FoodTruck;
import ac.kr.deu.connect.luck.food_truck.repository.FoodTruckRepository;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Checker {
    private static final Logger log = LoggerFactory.getLogger(Checker.class);
    private final FoodTruckRepository foodTruckRepository;


    public boolean isFoodTruckManager(Long id) {

        log.info("id : " + id);
        FoodTruck foodTruck = foodTruckRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 푸드트럭이 존재하지 않습니다.")
        );

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("authentication.getName() : " + authentication.getName());
        log.info("foodTruck.getManager().getEmail() : " + foodTruck.getManager().getEmail());

        return authentication.getName().equals(foodTruck.getManager().getEmail());
    }
}

