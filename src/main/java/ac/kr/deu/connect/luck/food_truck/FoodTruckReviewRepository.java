package ac.kr.deu.connect.luck.food_truck;

import ac.kr.deu.connect.luck.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodTruckReviewRepository extends JpaRepository<FoodTruckReview, Long> {

    void deleteAllByFoodTruck(FoodTruck foodTruck);
    void deleteAllByAuthor(User author);
    List<FoodTruckReview> findByFoodTruck(FoodTruck foodTruck);
    List<FoodTruckReview> findByFoodTruckId(Long foodTruckId);
    List<FoodTruckReview> findByAuthor(User author);
}
