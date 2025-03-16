package ac.kr.deu.connect.luck.foodtruck.controller.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "푸드트럭 리뷰 답글 등록 요청 DTO")
data class FoodTruckReviewReplyRequest(
    @field:Schema(description = "리뷰 답글 내용", example = "감사합니다")
    val content: String
) {
}
