package ac.kr.deu.connect.luck.event.application;

import ac.kr.deu.connect.luck.event.entity.Event;
import ac.kr.deu.connect.luck.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventApplicationRepository extends JpaRepository<EventApplication, Long> {

    /**
     * 이벤트 신청자 목록 조회 (행사 관리자용 API)
     *
     * @param event 조회 하려는 이벤트
     * @return 이벤트에 신청한 신청자 목록
     */
    List<EventApplication> findAllByEvent(Event event);

    List<EventApplication> findAllByEventId(Long eventId);

    /**
     * 이벤트 신청자 목록 조회 (푸드트럭 매니저용 API)
     *
     * @param user 조회 하려는 푸드트럭 매니저
     * @return 푸드트럭 매니저가 신청한 이벤트 목록
     */
    List<EventApplication> findAllByFoodTruckManager(User user);


    List<EventApplication> findAllByEventManager(User user);

    List<EventApplication> findAllByEventManagerAndEventId(User user, Long eventId);
}
