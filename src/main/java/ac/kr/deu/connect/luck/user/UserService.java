package ac.kr.deu.connect.luck.user;

import ac.kr.deu.connect.luck.auth.SignUpRequest;
import ac.kr.deu.connect.luck.event.EventRepository;
import ac.kr.deu.connect.luck.exception.CustomErrorCode;
import ac.kr.deu.connect.luck.exception.CustomException;
import ac.kr.deu.connect.luck.food_truck.FoodTruck;
import ac.kr.deu.connect.luck.food_truck.FoodTruckMenuRepository;
import ac.kr.deu.connect.luck.food_truck.FoodTruckRepository;
import ac.kr.deu.connect.luck.review.Review;
import ac.kr.deu.connect.luck.review.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
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
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException(CustomErrorCode.ID_NOT_MATCH));
        reviewRepository.deleteByWriter(user);
        eventRepository.deleteByManager(user);
        for (FoodTruck foodTruck : foodTruckRepository.findByManager(user)) {
            foodTruckMenuRepository.deleteByFoodTruck(foodTruck);
        }
        foodTruckRepository.deleteByManager(user);
        userRepository.delete(user);
        return "delete success";
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new CustomException(CustomErrorCode.ID_NOT_MATCH));
    }

    public User updateUser(Long id, SignUpRequest user) {
        User findUser = userRepository.findById(id).orElseThrow(() -> new CustomException(CustomErrorCode.ID_NOT_MATCH));
        if (user.email() != null) findUser.setEmail(user.email());
        if (user.password() != null) findUser.setPassword(user.password());
        if (user.name() != null) findUser.setName(user.name());
        if (user.phoneNumber() != null) findUser.setPhone(user.phoneNumber());
        return userRepository.save(findUser);
    }

    public UserInfo findUserInfo(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException(CustomErrorCode.ID_NOT_MATCH));
        List<Review> reviews = reviewRepository.findByWriter_Id(id);

        return new UserInfo(user, reviews);
    }
}
