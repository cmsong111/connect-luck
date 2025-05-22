package ac.kr.deu.connect.luck.foodtruck.service.data

import ac.kr.deu.connect.luck.foodtruck.entity.FoodTruck
import ac.kr.deu.connect.luck.foodtruck.entity.FoodTruckCategory

data class FoodTruckSummaryData(
    val id: Long = 0L,
    var name: String,
    var description: String?,
    var thumbnail: String,
    var type: FoodTruckCategory,
    var averageRating: Double,
    var reviewCount: Int,
    var managerId: Long,
) {
    companion object {
        fun from(foodTruck: FoodTruck): FoodTruckSummaryData {
            return FoodTruckSummaryData(
                id = foodTruck.id,
                name = foodTruck.name,
                description = foodTruck.description,
                thumbnail = foodTruck.thumbnailUrl,
                type = foodTruck.category,
                averageRating = foodTruck.averageRating,
                reviewCount = foodTruck.reviewCount,
                managerId = foodTruck.managerId,
            )
        }
    }
}
