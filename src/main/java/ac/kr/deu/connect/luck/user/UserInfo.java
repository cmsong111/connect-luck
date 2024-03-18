package ac.kr.deu.connect.luck.user;

import ac.kr.deu.connect.luck.review.Review;

import java.util.List;

public record UserInfo(
        User user,
        List<Review> reviews
) {
}
