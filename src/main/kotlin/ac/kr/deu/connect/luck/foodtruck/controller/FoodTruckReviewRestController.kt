package ac.kr.deu.connect.luck.foodtruck.controller

import ac.kr.deu.connect.luck.common.controller.data.AuthenticatedUser
import ac.kr.deu.connect.luck.configuration.SpringDocConfig.Companion.BEARER_AUTH
import ac.kr.deu.connect.luck.configuration.SpringDocConfig.Companion.FOOD_TRUCK_REVIEW
import ac.kr.deu.connect.luck.foodtruck.controller.request.FoodTruckReviewCreateForm
import ac.kr.deu.connect.luck.foodtruck.controller.request.FoodTruckReviewReplyRequest
import ac.kr.deu.connect.luck.foodtruck.controller.response.FoodTruckReviewResponse
import ac.kr.deu.connect.luck.foodtruck.service.FoodTruckReviewService
import ac.kr.deu.connect.luck.user.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.data.web.PagedModel
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/food-trucks/{foodTruckId}/reviews")
@Tag(name = FOOD_TRUCK_REVIEW, description = "Food Truck Review API")
class FoodTruckReviewRestController(
    private val foodTruckReviewService: FoodTruckReviewService,
    private val userService: UserService,
) {
    @GetMapping
    @Operation(summary = "리뷰 목록 조회", description = "푸드트럭 리뷰 목록을 조회합니다.")
    fun getReviews(
        @PathVariable foodTruckId: Long,
        @PageableDefault(size = 10, page = 0) @ParameterObject pageable: Pageable,
    ): ResponseEntity<PagedModel<FoodTruckReviewResponse>> {
        return ResponseEntity.ok(
            PagedModel(
                foodTruckReviewService.getReviewsByFoodTruckId(foodTruckId, pageable).map {
                    FoodTruckReviewResponse.from(
                        review = it,
                        author = userService.getUserById(it.authorId)
                    )
                }
            )
        )
    }

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @Operation(summary = "리뷰 등록", description = "푸드트럭 리뷰를 등록합니다.")
    @SecurityRequirement(name = BEARER_AUTH)
    fun createReview(
        @PathVariable foodTruckId: Long,
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
        foodTruckReviewRequest: FoodTruckReviewCreateForm,
    ): ResponseEntity<FoodTruckReviewResponse> {
        return ResponseEntity.ok(
            FoodTruckReviewResponse.from(
                foodTruckReviewService.createReview(
                    foodTruckId = foodTruckId,
                    userId = authenticatedUser.userId,
                    foodTruckReviewRequest = foodTruckReviewRequest
                ),
                author = userService.getUserById(authenticatedUser.userId)
            )
        )
    }

    @PatchMapping("/{reviewId}", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @Operation(summary = "리뷰 수정", description = "푸드트럭 리뷰를 수정합니다.")
    @SecurityRequirement(name = BEARER_AUTH)
    fun updateReview(
        @PathVariable foodTruckId: Long,
        @PathVariable reviewId: Long,
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
        foodTruckReviewRequest: FoodTruckReviewCreateForm,
    ): ResponseEntity<FoodTruckReviewResponse> {
        return ResponseEntity.ok(
            FoodTruckReviewResponse.from(
                foodTruckReviewService.updateReview(
                    reviewId = reviewId,
                    userId = authenticatedUser.userId,
                    foodTruckReviewRequest = foodTruckReviewRequest
                ),
                author = userService.getUserById(authenticatedUser.userId)
            )
        )
    }

    @PostMapping("/{reviewId}/reply")
    @Operation(summary = "리뷰 답글 등록 (푸드트럭 매니저용)", description = "푸드트럭 리뷰에 답글을 등록합니다.")
    @SecurityRequirement(name = BEARER_AUTH)
    fun createReply(
        @PathVariable foodTruckId: Long,
        @PathVariable reviewId: Long,
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
        @RequestBody foodTruckReviewReplyRequest: FoodTruckReviewReplyRequest
    ): ResponseEntity<FoodTruckReviewResponse> {
        return ResponseEntity.ok(
            FoodTruckReviewResponse.from(
                foodTruckReviewService.createReply(
                    reviewId = reviewId,
                    userId = authenticatedUser.userId,
                    reply = foodTruckReviewReplyRequest.content
                ),
                author = userService.getUserById(authenticatedUser.userId)
            )
        )
    }

    @DeleteMapping("/{reviewId}")
    @Operation(summary = "리뷰 삭제", description = "푸드트럭 리뷰를 삭제합니다.")
    @SecurityRequirement(name = BEARER_AUTH)
    fun deleteReview(
        @PathVariable foodTruckId: Long,
        @PathVariable reviewId: Long,
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
    ): ResponseEntity<Any> {
        foodTruckReviewService.deleteReview(
            reviewId = reviewId,
            userId = authenticatedUser.userId
        )
        return ResponseEntity.noContent().build()
    }
}
