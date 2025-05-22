package ac.kr.deu.connect.luck.foodtruck.repository

import ac.kr.deu.connect.luck.foodtruck.entity.FoodTruck
import ac.kr.deu.connect.luck.foodtruck.entity.FoodTruckCategory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface FoodTruckRepository : JpaRepository<FoodTruck, Long> {

    @Query(
        """
        select foodTruck from FoodTruck foodTruck
        WHERE (:category is null or foodTruck.category = :category) and
              (:managerId is null or foodTruck.managerId = :managerId)
    """
    )
    fun findFoodTrucks(
        category: FoodTruckCategory? = null,
        managerId: Long? = null,
        pageable: Pageable,
    ): Page<FoodTruck>
}
