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
     * 내가 신청한 이벤트 목록 조회
     * 푸드트럭 매니저만 사용 가능합니다
     *
     * @param email 현재 사용자 정보
     */
    public List<EventApplication> getMyEventApplicationsForTruckManager(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(CustomErrorCode.USER_ID_NOT_MATCH));
        return eventApplicationRepository.findAllByFoodTruckManager(user);
    }

    /**
     * 내 이벤트에 대한 신청 목록 조회
     * 이벤트 관리자만 사용 가능합니다
     *
     * @param eventId 이벤트 UID
     *                이벤트 관리자만 사용 가능합니다
     * @param email   현재 사용자 정보
     */
    public List<EventApplication> getMyEventApplicationsForEventManager(Long eventId, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(CustomErrorCode.USER_ID_NOT_MATCH));
        if (eventId == null) {
            return eventApplicationRepository.findAllByEventManager(user);
        }
        return eventApplicationRepository.findAllByEventManagerAndEventId(user, eventId);
    }

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

        Event event = eventRepository.findById(eventId).orElseThrow(() -> new CustomException(CustomErrorCode.EVENT_NOT_FOUND));

        eventApplicationSaved.setFoodTruckManager(user);
        eventApplicationSaved.setEvent(event);
        eventApplicationSaved.setStatus(ApplicationStatus.PENDING);

        return eventApplicationRepository.save(eventApplicationSaved);
    }

    /**
     * 특정 이벤트에 대한 푸드트럭 신청 승인 / 거절
     * 이벤트 관리자만 승인 가능합니다
     *
     * @param eventId       이벤트 UID
     *                      이벤트 관리자만 승인 가능합니다
     * @param applicationId 신청 UID
     *                      이벤트 관리자만 승인 가능합니다
     * @return 승인 결과
     */
    public EventApplication approveEventApplication(Long eventId, Long applicationId, ApplicationStatus applicationStatus) {
        EventApplication eventApplication = eventApplicationRepository.findById(applicationId).orElseThrow(() -> new IllegalArgumentException("지원서가 존재하지 않습니다"));
        if (!eventApplication.getEvent().getId().equals(eventId)) {
            throw new IllegalArgumentException("이벤트 ID가 일치하지 않습니다");
        }
        eventApplication.setStatus(applicationStatus);
        return eventApplicationRepository.save(eventApplication);
    }

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
