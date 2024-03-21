package ac.kr.deu.connect.luck.food_truck;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodTruckMenuRepository extends JpaRepository<FoodTruckMenu, Long> {

    void deleteAllByFoodTruck(FoodTruck foodTruck);
}
