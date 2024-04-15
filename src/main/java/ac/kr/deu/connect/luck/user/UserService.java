package ac.kr.deu.connect.luck.user;

import ac.kr.deu.connect.luck.auth.SignUpRequest;
import ac.kr.deu.connect.luck.event.EventRepository;
import ac.kr.deu.connect.luck.exception.CustomErrorCode;
import ac.kr.deu.connect.luck.exception.CustomException;
import ac.kr.deu.connect.luck.food_truck.entity.FoodTruck;
import ac.kr.deu.connect.luck.food_truck.entity.FoodTruckReview;
import ac.kr.deu.connect.luck.food_truck.repository.FoodTruckMenuRepository;
import ac.kr.deu.connect.luck.food_truck.repository.FoodTruckRepository;
import ac.kr.deu.connect.luck.food_truck.repository.FoodTruckReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final FoodTruckReviewRepository foodTruckReviewRepository;
    private final EventRepository eventRepository;
    private final FoodTruckRepository foodTruckRepository;
    private final FoodTruckMenuRepository foodTruckMenuRepository;

    /**
     * 유저 삭제
     * 리뷰, 이벤트, 푸드트럭, 메뉴 등 모두 삭제
     *
     * @param id 유저 id
     * @return String "delete success"
     */
    @Transactional
    public String deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException(CustomErrorCode.USER_ID_NOT_MATCH));
        foodTruckReviewRepository.deleteAllByAuthor(user);
        eventRepository.deleteByManager(user);
        for (FoodTruck foodTruck : foodTruckRepository.findByManager(user)) {
            foodTruckMenuRepository.deleteAllByFoodTruck(foodTruck);
        }
        foodTruckRepository.deleteByManager(user);
        userRepository.delete(user);
        return "delete success";
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new CustomException(CustomErrorCode.USER_ID_NOT_MATCH));
    }

    public User updateUser(Long id, SignUpRequest user) {
        User findUser = userRepository.findById(id).orElseThrow(() -> new CustomException(CustomErrorCode.USER_ID_NOT_MATCH));
        if (user.email() != null) findUser.setEmail(user.email());
        if (user.password() != null) findUser.setPassword(user.password());
        if (user.name() != null) findUser.setName(user.name());
        if (user.phoneNumber() != null) findUser.setPhone(user.phoneNumber());
        return userRepository.save(findUser);
    }

    public UserInfo findUserInfo(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException(CustomErrorCode.USER_ID_NOT_MATCH));
        List<FoodTruckReview> reviews = foodTruckReviewRepository.findByAuthor(user);

        return new UserInfo(user, reviews);
    }

    public User setUserRole(Long id, UserRole role) {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException(CustomErrorCode.USER_ID_NOT_MATCH));
        if (user.getRole() == role) {
            throw new CustomException(CustomErrorCode.ALREADY_SET_ROLE);
        }
        // 푸드트럭 매니저는 이벤트 매니저로 변경 불가
        if (user.getRole() == UserRole.FOOD_TRUCK_MANAGER && role == UserRole.EVENT_MANAGER) {
            throw new CustomException(CustomErrorCode.ROLE_NOT_BE_CHANGE);
        }
        // 이벤트 매니저는 푸드트럭 매니저로 변경 불가
        if (user.getRole() == UserRole.EVENT_MANAGER && role == UserRole.FOOD_TRUCK_MANAGER) {
            throw new CustomException(CustomErrorCode.ROLE_NOT_BE_CHANGE);
        }
        user.setRole(role);
        return userRepository.save(user);
    }
}
