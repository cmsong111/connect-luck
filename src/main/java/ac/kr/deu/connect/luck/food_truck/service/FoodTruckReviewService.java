package ac.kr.deu.connect.luck.food_truck.service;

import ac.kr.deu.connect.luck.food_truck.FoodTruckMapper;
import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckReviewRequest;
import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckReviewResponse;
import ac.kr.deu.connect.luck.food_truck.entity.FoodTruck;
import ac.kr.deu.connect.luck.food_truck.entity.FoodTruckReview;
import ac.kr.deu.connect.luck.food_truck.repository.FoodTruckRepository;
import ac.kr.deu.connect.luck.food_truck.repository.FoodTruckReviewRepository;
import ac.kr.deu.connect.luck.image.ImageUploader;
import ac.kr.deu.connect.luck.user.User;
import ac.kr.deu.connect.luck.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodTruckReviewService {

    private final FoodTruckRepository foodTruckRepository;
    private final FoodTruckReviewRepository foodTruckReviewRepository;
    private final UserRepository userRepository;
    private final ImageUploader imageUploader;
    private final FoodTruckMapper foodTruckMapper;

    /**
     * 푸드트럭 리뷰 등록
     *
     * @param foodTruckId            푸드트럭 ID
     * @param foodTruckReviewRequest 푸드트럭 리뷰 요청
     * @param userEmail              사용자 이메일
     */
    public FoodTruckReviewResponse saveFoodTruckReview(Long foodTruckId, FoodTruckReviewRequest foodTruckReviewRequest, String userEmail) {
        FoodTruck foodTruck = FoodTruck.builder().id(foodTruckId).build();
        User author = userRepository.findByEmail(userEmail).orElseThrow();

        FoodTruckReview review = foodTruckMapper.toFoodTruckReview(foodTruckReviewRequest);

        review.setFoodTruck(foodTruck);
        review.setAuthor(author);

        if (foodTruckReviewRequest.getImage() != null) {
            String imageUrl = imageUploader.uploadImage(foodTruckReviewRequest.getImage()).getData().getUrl();
            review.setImageUrl(imageUrl);
        }

        return foodTruckMapper.toFoodTruckReviewResponse(foodTruckReviewRepository.save(review));
    }

    /**
     * 푸드트럭 리뷰 수정
     *
     * @param foodTruckId            푸드트럭 ID
     * @param foodTruckReviewRequest 푸드트럭 리뷰 요청
     * @param userEmail              사용자 이메일
     * @param reviewID               리뷰 ID
     * @return 수정된 리뷰
     */
    public FoodTruckReviewResponse saveFoodTruckReview(Long foodTruckId, FoodTruckReviewRequest foodTruckReviewRequest, String userEmail, Long reviewID) {
        FoodTruckReview review = foodTruckReviewRepository.findById(reviewID).orElseThrow();

        review.setRating(foodTruckReviewRequest.getRating());
        review.setContent(foodTruckReviewRequest.getContent());

        if (foodTruckReviewRequest.getImage() != null) {
            String imageUrl = imageUploader.uploadImage(foodTruckReviewRequest.getImage()).getData().getUrl();
            review.setImageUrl(imageUrl);
        }

        return foodTruckMapper.toFoodTruckReviewResponse(foodTruckReviewRepository.save(review));
    }

    /**
     * 푸드트럭 리뷰 삭제
     *
     * @param reviewId 리뷰 ID
     */
    @Transactional
    public void deleteFoodTruckReview(Long reviewId) {
        foodTruckReviewRepository.deleteById(reviewId);
    }

    /**
     * 푸드트럭 리뷰 답글 등록
     *
     * @param reviewId 리뷰 ID
     * @param content  답글 내용
     * @return 답글이 달린 리뷰
     */
    public FoodTruckReviewResponse addReplyToReview(Long reviewId, String content) {
        FoodTruckReview review = foodTruckReviewRepository.findById(reviewId).orElseThrow();
        review.setReply(content);
        return foodTruckMapper.toFoodTruckReviewResponse(foodTruckReviewRepository.save(review));
    }

    public FoodTruckReview getFoodTruckReview(Long reviewId) {
        return foodTruckReviewRepository.findById(reviewId).get();
    }
}
