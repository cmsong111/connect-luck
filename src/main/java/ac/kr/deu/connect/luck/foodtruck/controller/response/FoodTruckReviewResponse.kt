package ac.kr.deu.connect.luck.foodtruck.controller.response

import ac.kr.deu.connect.luck.foodtruck.entity.FoodTruckReview
import ac.kr.deu.connect.luck.user.controller.response.UserSummaryResponse
import ac.kr.deu.connect.luck.user.entity.User
import io.swagger.v3.oas.annotations.media.Schema
import java.time.Instant

@Schema(description = "푸드트럭 리뷰 응답 DTO")
data class FoodTruckReviewResponse(
    @Schema(description = "리뷰 ID", example = "1")
    val id: Long = 0L,
    @Schema(description = "리뷰 작성자 정보")
    val author: UserSummaryResponse,
    @Schema(description = "리뷰 내용", example = "맛있어요!")
    var content: String,
    @Schema(description = "평점", example = "5")
    var rating: Int,
    @Schema(description = "이미지 목록", example = "[\"image1.jpg\", \"image2.jpg\"]")
    val images: List<String>,
    @Schema(description = "답글", example = "감사합니다!")
    var reply: String? = null,
    @Schema(description = "리뷰 작성일", example = "2021-08-01T00:00:00Z")
    val createdAt: Instant = Instant.now(),
) {
    companion object {
        fun from(
            review: FoodTruckReview,
            author: User,
        ): FoodTruckReviewResponse {
            return FoodTruckReviewResponse(
                id = review.id,
                author = UserSummaryResponse.from(author),
                content = review.content,
                rating = review.rating,
                images = review.images ?: emptyList(),
                reply = review.reply,
                createdAt = review.createdAt,
            )
        }
    }
}
