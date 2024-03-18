package ac.kr.deu.connect.luck.review;

import ac.kr.deu.connect.luck.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    void deleteByWriter(User writer);

    List<Review> findByWriter_Id(Long id);
}
