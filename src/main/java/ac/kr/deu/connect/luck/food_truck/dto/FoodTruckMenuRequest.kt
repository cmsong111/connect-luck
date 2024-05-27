package ac.kr.deu.connect.luck.food_truck.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.multipart.MultipartFile

@Schema(description = "푸드트럭 메뉴 등록 요청 DTO")
data class FoodTruckMenuRequest(
    @field:Schema(description = "메뉴명", example = "햄버거")
    val name: String,
    @field:Schema(description = "가격", example = "5000")
    val price: Int,
    @field:Schema(description = "설명", example = "맛있는 햄버거")
    val description: String,
    @field:Schema(description = "이미지", example = "image.jpg")
    val image: MultipartFile?
)
