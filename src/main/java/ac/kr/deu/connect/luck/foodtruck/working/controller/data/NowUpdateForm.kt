package ac.kr.deu.connect.luck.foodtruck.working.controller.data

import ac.kr.deu.connect.luck.foodtruck.entity.FoodTruckWorking

data class NowUpdateForm (
    val latitude: Double,
    val longitude: Double,
    val status: FoodTruckWorking.Status,
) {
}
