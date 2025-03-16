package ac.kr.deu.connect.luck.foodtruck.repository

import ac.kr.deu.connect.luck.foodtruck.entity.FoodTruckReview
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FoodTruckReviewRepository : JpaRepository<FoodTruckReview, Long> {
    fun findAllByFoodTruckId(
        foodTruckId: Long,
        pageable: Pageable,
    ): Page<FoodTruckReview>

    fun findAllByAuthorId(
        foodTruckId: Long,
        pageable: Pageable,
    ): Page<FoodTruckReview>
}
