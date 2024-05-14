package ac.kr.deu.connect.luck.food_truck.dto

import ac.kr.deu.connect.luck.food_truck.entity.FoodType
import org.springframework.web.multipart.MultipartFile

data class FoodTruckRequestV2(
    var name: String,
    var description: String,
    var image: MultipartFile?,
    var foodType: FoodType,
) {
    override fun toString(): String {
        return "FoodTruckRequestV2(name='$name', description='$description', image=$image, foodType=$foodType)"
    }
}
