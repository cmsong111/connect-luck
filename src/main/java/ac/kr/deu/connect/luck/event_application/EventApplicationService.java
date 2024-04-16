package ac.kr.deu.connect.luck.event_application;

import ac.kr.deu.connect.luck.event.Event;
import ac.kr.deu.connect.luck.event.EventRepository;
import ac.kr.deu.connect.luck.exception.CustomErrorCode;
import ac.kr.deu.connect.luck.exception.CustomException;
import ac.kr.deu.connect.luck.user.User;
import ac.kr.deu.connect.luck.user.UserRepository;
import ac.kr.deu.connect.luck.user.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EventApplicationService {

    private final EventApplicationRepository eventApplicationRepository;
    private final EventApplicationMapper eventApplicationMapper;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;


    /**
     * 지원서 신규 작성 메소드
     *
     * @param eventApplicationRequest 이벤트 요청 폼
     * @param eventId                 이벤트 키
     * @param email                   신청자(푸드트럭 매니저) 이메일
     * @return 생성된 신규 지원서
     */
    public EventApplication createEventApplication(EventApplicationRequest eventApplicationRequest, Long eventId, String email) {
        EventApplication eventApplicationSaved = eventApplicationMapper.toEventApplication(eventApplicationRequest);

        // 유저 정보 조회
        User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(CustomErrorCode.USER_ID_NOT_MATCH));

        if (user.getRoles().contains(UserRole.ADMIN)) {
            throw new CustomException(CustomErrorCode.USER_ID_NOT_MATCH);
        }

        Event evnet = eventRepository.findById(eventId).orElseThrow(() -> new CustomException(CustomErrorCode.EVENT_NOT_FOUND));

        eventApplicationSaved.setFoodTruckManager(user);
        eventApplicationSaved.setEvent(evnet);
        eventApplicationSaved.setStatus(ApplicationStatus.PENDING);

        return eventApplicationRepository.save(eventApplicationSaved);
    }

    //TODO: 지원서 취소(푸드트럭 매니저용)

    //TODO: 지원서 status 상태 변경(승인,거절) (행사 관리자용)

    /**
     * 이벤트에 대한 모든 지원서 조회
     *
     * @param eventId 조회 하려는 이벤트 ID
     * @return 이벤트에 대한 모든 지원서 목록
     */
    List<EventApplication> getEventApplications(Long eventId) {
        return eventApplicationRepository.findAllByEventId(eventId);
    }

    /**
     * 모든 지원서 조회 (관리자 용)
     *
     * @return 모든 지원서 목록
     */
    List<EventApplication> getEventApplications() {
        return eventApplicationRepository.findAll();
    }
}
