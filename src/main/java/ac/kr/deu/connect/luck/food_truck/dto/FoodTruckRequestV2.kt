package ac.kr.deu.connect.luck.food_truck.dto

import ac.kr.deu.connect.luck.food_truck.entity.FoodType
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.multipart.MultipartFile

@Schema(description = "푸드트럭 등록 요청 DTO")
data class FoodTruckRequestV2(
    @field:Schema(description = "상호명", example = "맛있는 푸드트럭")
    var name: String,
    @field:Schema(description = "설명", example = "맛있는 음식을 판매하는 푸드트럭입니다.")
    var description: String,
    @field:Schema(description = "이미지", nullable = true)
    var image: MultipartFile?,
    @field:Schema(description = "음식 종류", example = "KOREAN")
    var foodType: FoodType,
) {
    override fun toString(): String {
        return "FoodTruckRequestV2(name='$name', description='$description', image=$image, foodType=$foodType)"
    }
}
