package ac.kr.deu.connect.luck.foodtruck.service.data

import ac.kr.deu.connect.luck.foodtruck.entity.FoodTruck
import ac.kr.deu.connect.luck.foodtruck.entity.FoodType

data class FoodTruckSummaryData(
    val id: Long = 0L,
    var name: String,
    var description: String? = null,
    var thumbnail: String,
    var type: FoodType = FoodType.ETC,
    var managerId: Long,
) {
    companion object {
        fun from(foodTruck: FoodTruck): FoodTruckSummaryData {
            return FoodTruckSummaryData(
                id = foodTruck.id,
                name = foodTruck.name,
                description = foodTruck.description,
                thumbnail = foodTruck.thumbnail,
                type = foodTruck.type,
                managerId = foodTruck.managerId,
            )
        }
    }
}
