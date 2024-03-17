package ac.kr.deu.connect.luck.review;

import ac.kr.deu.connect.luck.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    long deleteByWriter(User writer);
}
