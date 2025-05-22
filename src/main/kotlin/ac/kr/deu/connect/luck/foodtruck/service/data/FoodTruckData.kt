package ac.kr.deu.connect.luck.foodtruck.service.data

import ac.kr.deu.connect.luck.foodtruck.entity.FoodTruck
import ac.kr.deu.connect.luck.foodtruck.entity.FoodTruckMenu
import ac.kr.deu.connect.luck.foodtruck.entity.FoodTruckCategory

data class FoodTruckData(
    val id: Long = 0L,
    var name: String,
    var description: String? = null,
    var thumbnail: String,
    var type: FoodTruckCategory = FoodTruckCategory.ETC,
    var managerId: Long,
    var images: MutableList<String> = mutableListOf(),
    var menus: MutableList<FoodTruckMenu> = mutableListOf(),
) {
    companion object {
        fun from(foodTruck: FoodTruck): FoodTruckData {
            return FoodTruckData(
                id = foodTruck.id,
                name = foodTruck.name,
                description = foodTruck.description,
                thumbnail = foodTruck.thumbnailUrl,
                type = foodTruck.category,
                managerId = foodTruck.managerId,
                images = foodTruck.images,
                menus = foodTruck.menus,
            )
        }
    }
}
