package ac.kr.deu.connect.luck.event;

import ac.kr.deu.connect.luck.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    void deleteByManager(User manager);
}
