package ac.kr.deu.connect.luck.foodtruck.repository;

import ac.kr.deu.connect.luck.foodtruck.entity.FoodTruck;
import ac.kr.deu.connect.luck.foodtruck.entity.FoodTruckMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodTruckMenuRepository extends JpaRepository<FoodTruckMenu, Long> {

    void deleteAllByFoodTruck(FoodTruck foodTruck);

    List<FoodTruckMenu> findByFoodTruck(FoodTruck foodTruck);

    List<FoodTruckMenu> findByFoodTruckId(Long foodTruckId);
}
