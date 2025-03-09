package ac.kr.deu.connect.luck.event.repository;

import ac.kr.deu.connect.luck.event.entity.EventStatus;
import ac.kr.deu.connect.luck.event.entity.Event;
import ac.kr.deu.connect.luck.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    void deleteByManager(User manager);

    List<Event> findAllByStatus(EventStatus status);

    List<Event> findAllByManager(User manager);
}
