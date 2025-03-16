package ac.kr.deu.connect.luck.foodtruck.working.controller.data

import ac.kr.deu.connect.luck.foodtruck.entity.FoodTruckWorking

data class NowRequest(
    val latitude: Double,
    val longitude: Double,
    val status: FoodTruckWorking.Status? = null,
    val radiusKm: Double = 5.0,
    val size: Int = 30,
)
