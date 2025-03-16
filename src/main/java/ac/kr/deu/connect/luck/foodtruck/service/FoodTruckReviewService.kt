package ac.kr.deu.connect.luck.foodtruck.service

import ac.kr.deu.connect.luck.common.storage.StorageService
import ac.kr.deu.connect.luck.foodtruck.controller.request.FoodTruckReviewCreateForm
import ac.kr.deu.connect.luck.foodtruck.entity.FoodTruckReview
import ac.kr.deu.connect.luck.foodtruck.repository.FoodTruckRepository
import ac.kr.deu.connect.luck.foodtruck.repository.FoodTruckReviewRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FoodTruckReviewService(
    private val storageService: StorageService,
    private val foodTruckRepository: FoodTruckRepository,
    private val foodTruckReviewRepository: FoodTruckReviewRepository,
) {
    /**
     * 푸드트럭 리뷰를 작성합니다.
     * @param foodTruckId 푸드트럭 식별자
     * @param userId 사용자 식별자
     * @param foodTruckReviewRequest 푸드트럭 리뷰 요청 폼
     * @return 작성된 리뷰
     */
    @Transactional
    fun createReview(
        foodTruckId: Long,
        userId: Long,
        foodTruckReviewRequest: FoodTruckReviewCreateForm,
    ): FoodTruckReview {
        return foodTruckReviewRepository.save(
            FoodTruckReview.create(
                foodTruckId = foodTruckId,
                authorId = userId,
                content = foodTruckReviewRequest.content,
                images = foodTruckReviewRequest.images?.map { storageService.save(it) },
                rating = foodTruckReviewRequest.rating,
            )
        )
    }

    /**
     * 푸드트럭 리뷰를 수정합니다.
     * @param reviewId 리뷰 식별자
     * @param userId 사용자 식별자
     * @param foodTruckReviewRequest 푸드트럭 리뷰 요청 폼
     * @return 수정된 리뷰
     */
    @Transactional
    fun updateReview(
        reviewId: Long,
        userId: Long,
        foodTruckReviewRequest: FoodTruckReviewCreateForm,
    ): FoodTruckReview {
        val review = foodTruckReviewRepository.findByIdOrNull(reviewId) ?: throw IllegalArgumentException("Review not found")
        if (review.authorId != userId) {
            throw IllegalArgumentException("User is not the author of the review")
        }

        review.content = foodTruckReviewRequest.content
        review.images = foodTruckReviewRequest.images?.map { storageService.save(it) }
        review.rating = foodTruckReviewRequest.rating

        return review
    }

    /**
     * 푸드트럭 리뷰 답글 작성합니다.
     * @param reviewId 리뷰 식별자
     * @param userId 사용자 식별자(푸드트럭 매니저)
     * @param reply 답글 내용
     * @return 리뷰
     */
    @Transactional
    fun createReply(
        reviewId: Long,
        userId: Long,
        reply: String,
    ): FoodTruckReview {
        val review = foodTruckReviewRepository.findByIdOrNull(reviewId)
            ?: throw IllegalArgumentException("Review not found")

        review.reply = reply
        return review
    }


    /**
     * 푸드트럭 리뷰를 삭제합니다.
     * @param reviewId 리뷰 식별자
     * @param userId 사용자 식별자
     */
    @Transactional
    fun deleteReview(
        reviewId: Long,
        userId: Long,
    ) {
        val review = foodTruckReviewRepository.findByIdOrNull(reviewId) ?: throw IllegalArgumentException("Review not found")
        if (review.authorId != userId) {
            throw IllegalArgumentException("User is not the author of the review")
        }

        foodTruckReviewRepository.delete(review)
    }

    /**
     * 푸드트럭 리뷰를 조회합니다. (푸드트럭 리뷰 조회)
     * @param foodTruckId 푸드트럭 식별자
     * @return 푸드트럭 리뷰 목록
     */
    @Transactional(readOnly = true)
    fun getReviewsByFoodTruckId(
        foodTruckId: Long,
        pageable: Pageable,
    ): Page<FoodTruckReview> {
        return foodTruckReviewRepository.findAllByFoodTruckId(foodTruckId, pageable)
    }

    /**
     * 푸드트럭 리뷰를 조회합니다. (작성자 리뷰 조회)
     * @param userId 사용자 식별자
     * @return 작성한 리뷰 목록
     */
    @Transactional(readOnly = true)
    fun getReviewsByUserId(
        userId: Long,
        pageable: Pageable,
    ): Page<FoodTruckReview> {
        return foodTruckReviewRepository.findAllByAuthorId(userId, pageable)
    }
}
