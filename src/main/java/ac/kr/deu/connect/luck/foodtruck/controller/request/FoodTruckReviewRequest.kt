package ac.kr.deu.connect.luck.foodtruck.controller.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.springframework.web.multipart.MultipartFile

@Schema(description = "푸드트럭 리뷰 등록 요청 DTO")
data class FoodTruckReviewRequest(
    @field:Schema(description = "리뷰 내용", example = "맛있어요")
    val content: String,
    @field:Schema(description = "평점 1~5 사이값만 입력하십시오", example = "5")
    @field:Min(1) @field:Max(5)
    val rating: Int,
    @field:Schema(description = "이미지", example = "image.jpg")
    val image: MultipartFile?
) {
    override fun toString(): String {
        return "FoodTruckReviewRequest(content='$content', rating=$rating image=${image?.originalFilename})"
    }
}
