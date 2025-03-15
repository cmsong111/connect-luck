package ac.kr.deu.connect.luck.foodtruck.working;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NowRepository extends JpaRepository<Now, Long> {

    Optional<Now> findByFoodTruckId(Long foodTruckId);

    List<Now> findByLatitudeBetweenAndLongitudeBetweenAndIsOperating(Double latitudeStart, Double latitudeEnd, Double longitudeStart, Double longitudeEnd, Boolean isOperating);

    List<Now> findByIsOperating(Boolean isOperating);


    void deleteAllByFoodTruckId(Long foodTruckId);
}
