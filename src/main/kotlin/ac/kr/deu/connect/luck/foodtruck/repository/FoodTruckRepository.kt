package ac.kr.deu.connect.luck.foodtruck.repository

import ac.kr.deu.connect.luck.foodtruck.entity.FoodTruck
import ac.kr.deu.connect.luck.foodtruck.entity.FoodType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface FoodTruckRepository : JpaRepository<FoodTruck, Long> {

    @Query(
        """
        select foodTurck from FoodTruck foodTurck
        where (:name is null or foodTurck.name like %:name%) and
              (:type is null or foodTurck.type = :type) and
              (:managerId is null or foodTurck.managerId = :managerId)
    """
    )
    fun findFoodTrucks(
        name: String? = null,
        type: FoodType? = null,
        managerId: Long? = null,
        pageable: Pageable,
    ): Page<FoodTruck>
}
