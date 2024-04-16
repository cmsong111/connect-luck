package ac.kr.deu.connect.luck.food_truck.service;

import ac.kr.deu.connect.luck.food_truck.FoodTruckMapper;
import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckReviewResponse;
import ac.kr.deu.connect.luck.food_truck.entity.FoodTruck;
import ac.kr.deu.connect.luck.food_truck.entity.FoodTruckReview;
import ac.kr.deu.connect.luck.food_truck.repository.FoodTruckRepository;
import ac.kr.deu.connect.luck.food_truck.repository.FoodTruckReviewRepository;
import ac.kr.deu.connect.luck.image.ImageUploader;
import ac.kr.deu.connect.luck.user.User;
import ac.kr.deu.connect.luck.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
     * @param foodTruckId 푸드트럭 ID
     * @param userEmail   사용자 이메일
     * @param content     리뷰 내용
     * @param score       평점
     * @param image       이미지 파일
     */
    public FoodTruckReviewResponse createFoodTruckReview(Long foodTruckId, String userEmail, String content, int score, MultipartFile image) {
        FoodTruck foodTruck = foodTruckRepository.findById(foodTruckId)
                .orElseThrow(() -> new IllegalArgumentException("푸드트럭을 찾을 수 없습니다."));
        User author = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        FoodTruckReview review = FoodTruckReview.builder()
                .foodTruck(foodTruck)
                .author(author)
                .content(content)
                .score(score)
                .build();

        if (image != null) {
            String imageUrl = imageUploader.uploadImage(image).getData().getUrl();
            review.setImageUrl(imageUrl);
        }
        FoodTruckReview saved = foodTruckReviewRepository.save(review);
        return foodTruckMapper.toFoodTruckReviewResponse(saved);
    }

    /**
     * 푸드트럭 리뷰 수정
     *
     * @param id        리뷰 ID
     * @param userEmail 사용자 이메일
     * @param content   리뷰 내용
     * @param score     평점
     * @param image     이미지 파일
     */
    public FoodTruckReviewResponse updateFoodTruckReview(Long id, String userEmail, String content, int score, MultipartFile image) {
        FoodTruckReview review = foodTruckReviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));
        if (!review.getAuthor().getEmail().equals(userEmail)) {
            throw new IllegalArgumentException("리뷰 작성자만 수정할 수 있습니다.");
        }

        if (content != null) {
            review.setContent(content);
        }
        if (score > 0) {
            review.setScore(score);
        }
        if (image != null) {
            String imageUrl = imageUploader.uploadImage(image).getData().getUrl();
            review.setImageUrl(imageUrl);
        }
        FoodTruckReview saved = foodTruckReviewRepository.save(review);
        return foodTruckMapper.toFoodTruckReviewResponse(saved);
    }

    /**
     * 푸드트럭 리뷰 삭제
     *
     * @param id        리뷰 ID
     * @param userEmail 사용자 이메일
     */
    public void deleteFoodTruckReview(Long id, String userEmail) {
        FoodTruckReview review = foodTruckReviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));
        if (!review.getAuthor().getEmail().equals(userEmail)) {
            throw new IllegalArgumentException("리뷰 작성자만 삭제할 수 있습니다.");
        }
        foodTruckReviewRepository.delete(review);
    }

    /**
     * 푸드트럭 리뷰 답글 등록
     *
     * @param id        리뷰 ID
     * @param content   답글 내용
     * @param userEmail 관리자 이메일
     */
    public FoodTruckReviewResponse createFoodTruckReviewReply(Long id, String userEmail, String content) {
        FoodTruckReview review = foodTruckReviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));
        if (!review.getFoodTruck().getManager().getEmail().equals(userEmail)) {
            throw new IllegalArgumentException("푸드트럭 매니저만 답글을 등록할 수 있습니다.");
        }
        review.setReply(content);
        return foodTruckMapper.toFoodTruckReviewResponse(foodTruckReviewRepository.save(review));
    }
}
