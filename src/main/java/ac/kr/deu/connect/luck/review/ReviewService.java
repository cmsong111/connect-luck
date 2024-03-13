package ac.kr.deu.connect.luck.review;

import ac.kr.deu.connect.luck.configuration.MapStructMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MapStructMapper mapStructMapper;

    public Review createReview(ReviewRequestDto reviewRequestDto) {
        return reviewRepository.save(mapStructMapper.toReview(reviewRequestDto));
    }

    public Review updateReview(Long id, ReviewRequestDto reviewRequestDto) {
        Review review = reviewRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다.")
        );
        review.setScore(reviewRequestDto.score());
        review.setContent(reviewRequestDto.content());
        review.setImageUrl(reviewRequestDto.imageUrl());

        return reviewRepository.save(review);
    }

    public void deleteReview(Long id) {
        reviewRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다.")
        );
        reviewRepository.deleteById(id);
    }

    public Review replyReview(Long id, String reply) {
        Review review = reviewRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다.")
        );
        review.setReply(reply);
        return reviewRepository.save(review);
    }
}
