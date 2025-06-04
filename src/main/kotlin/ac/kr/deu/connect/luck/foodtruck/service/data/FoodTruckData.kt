package ac.kr.deu.connect.luck.foodtruck.service.data

import ac.kr.deu.connect.luck.foodtruck.entity.FoodTruck
import ac.kr.deu.connect.luck.foodtruck.entity.FoodTruckCategory
import ac.kr.deu.connect.luck.foodtruck.entity.FoodTruckMenu
import ac.kr.deu.connect.luck.foodtruck.entity.FoodTruckWorking

data class FoodTruckData(
    val id: Long,
    var name: String,
    var description: String?,
    var thumbnail: String,
    var category: FoodTruckCategory,
    var managerId: Long,
    var images: List<String> = listOf(),
    var menus: List<FoodTruckMenu> = listOf(),
    var working: FoodTruckWorking?,
    var averageRating: Double,
    var reviewCount: Int,
) {
    companion object {
        fun from(foodTruck: FoodTruck): FoodTruckData {
            return FoodTruckData(
                id = foodTruck.id,
                name = foodTruck.name,
                description = foodTruck.description,
                thumbnail = foodTruck.thumbnailUrl,
                category = foodTruck.category,
                managerId = foodTruck.managerId,
                images = foodTruck.images.toList(),
                menus = foodTruck.menus.toList(),
                working = foodTruck.working,
                averageRating = foodTruck.averageRating,
                reviewCount = foodTruck.reviewCount,
            )
        }
    }
}
