package ac.kr.deu.connect.luck.foodtruck.controller.response

import ac.kr.deu.connect.luck.foodtruck.entity.FoodTruckCategory
import ac.kr.deu.connect.luck.foodtruck.service.data.FoodTruckSummaryData
import ac.kr.deu.connect.luck.user.controller.response.UserSummaryResponse
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "푸드트럭 요약 응답 DTO", description = "푸드트럭 요약 응답 DTO")
data class FoodTruckSummaryResponse(
    @Schema(description = "식당 ID", example = "1")
    val id: Long = 0L,
    @Schema(description = "식당 이름", example = "맛있는 푸드트럭")
    var name: String,
    @Schema(description = "식당 설명", example = "맛있는 푸드트럭입니다.")
    var description: String? = null,
    @Schema(description = "식당 썸네일", example = "thumbnail.jpg")
    var thumbnail: String,
    @Schema(description = "식당 타입", example = "MEXICAN")
    var type: FoodTruckCategory = FoodTruckCategory.ETC,
    @Schema(description = "매니저 정보")
    var manager: UserSummaryResponse,
) {
    companion object {
        fun from(foodTruckSummaryData: FoodTruckSummaryData, manager: UserSummaryResponse): FoodTruckSummaryResponse {
            return FoodTruckSummaryResponse(
                id = foodTruckSummaryData.id,
                name = foodTruckSummaryData.name,
                description = foodTruckSummaryData.description,
                thumbnail = foodTruckSummaryData.thumbnail,
                type = foodTruckSummaryData.type,
                manager = manager
            )
        }
    }
}
