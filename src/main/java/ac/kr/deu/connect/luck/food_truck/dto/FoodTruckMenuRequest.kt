package ac.kr.deu.connect.luck.food_truck.dto

import org.springframework.web.multipart.MultipartFile

data class FoodTruckMenuRequest(
    val name: String,
    val price: Int,
    val description: String,
    val image: MultipartFile?
)
