package ac.kr.deu.connect.luck.review;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Review", description = "Review API")
@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewRestController {
    private final ReviewService reviewService;

    @PostMapping
    @Operation(summary = "Create a review", description = "리뷰를 생성합니다.")
    public ResponseEntity<Review> createReview(
            @RequestBody ReviewRequestDto reviewRequestDto
    ) {
        return ResponseEntity.ok(reviewService.createReview(reviewRequestDto));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update a review", description = "리뷰를 수정합니다.")
    public ResponseEntity<Review> updateReview(
            @PathVariable Long id, @RequestBody ReviewRequestDto reviewRequestDto
    ) {
        return ResponseEntity.ok(reviewService.updateReview(id, reviewRequestDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a review", description = "리뷰를 삭제합니다.")
    public ResponseEntity<String> deleteReview(
            @PathVariable Long id
    ) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok("리뷰가 삭제되었습니다.");
    }
}
