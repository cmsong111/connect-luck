package ac.kr.deu.connect.luck.event;

import ac.kr.deu.connect.luck.event.dto.EventDetailResponse;
import ac.kr.deu.connect.luck.event.dto.EventRequest;
import ac.kr.deu.connect.luck.event_application.EventApplication;
import ac.kr.deu.connect.luck.image.ImageUploader;
import ac.kr.deu.connect.luck.user.User;
import ac.kr.deu.connect.luck.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final UserRepository userRepository;
    private final ImageUploader imageUploader;

    /**
     * 이벤트 목록 조회
     *
     * @param eventStatus 이벤트 상태 조회 null이면 전체 조회
     * @return 이벤트 목록
     */
    public List<EventDetailResponse> getEvents(EventStatus eventStatus) {
        List<Event> events;
        if (eventStatus == null) {
            events = eventRepository.findAll();
        } else {
            events = eventRepository.findAllByStatus(eventStatus);
        }
        return events.stream().map(eventMapper::toEventResponse).toList();
    }

    /**
     * email 로 이벤트 목록 조회
     *
     * @param email 이벤트 매니저 email
     * @return 이벤트 목록
     */
    public List<EventDetailResponse> getEvents(String email) {
        List<Event> events;
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
        );
        events = eventRepository.findAllByManager(user);
        return events.stream().map(eventMapper::toEventResponse).toList();
    }

    /**
     * 이벤트 상세 조회
     *
     * @param id 이벤트 ID
     * @return 이벤트 상세 정보
     */
    public EventDetailResponse getEvent(Long id) {
        Event event = eventRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 이벤트가 존재하지 않습니다.")
        );
        return eventMapper.toEventResponse(event);
    }

    /**
     * 이벤트 생성
     *
     * @param eventRequest  이벤트 생성 요청 폼
     * @param multipartFile 이벤트 대표 이미지
     *                      null이면 기본 이미지 사용
     * @param managerEmail  매니저 이름
     * @return 생성된 이벤트
     */
    public Event createEvent(EventRequest eventRequest, MultipartFile multipartFile, String managerEmail) {
        Event eventSaved = eventMapper.toEvent(eventRequest);

        User user = userRepository.findByEmail(managerEmail).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
        );
        // 기본 이미지 설정 후 이미지가 있으면 업로드
        String image = "https://picsum.photos/1600/900";
        if (multipartFile != null) {
            image = imageUploader.uploadImage(multipartFile).getData().getUrl();
        }
        eventSaved.setImageUrl(image);
        eventSaved.setManager(user);
        eventSaved.setStatus(EventStatus.OPEN_FOR_APPLICATION);

        return eventRepository.save(eventSaved);
    }

    /**
     * 이벤트 수정
     *
     * @param id            이벤트 UID
     * @param eventRequest  이벤트 생성 요청 폼
     * @param multipartFile 이벤트 대표 이미지
     * @param managerEmail  이벤트 매니저 이메일
     * @return 수정된 이벤트
     */
    public Event updateEvent(Long id, EventRequest eventRequest, MultipartFile multipartFile, String managerEmail) {
        Event findEvent = eventRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 이벤트가 존재하지 않습니다.")
        );

        if (!findEvent.getManager().getEmail().equals(managerEmail)) {
            throw new IllegalArgumentException("해당 이벤트의 매니저가 아닙니다.");
        }

        if (multipartFile != null) {
            String image = imageUploader.uploadImage(multipartFile).getData().getUrl();
            findEvent.setImageUrl(image);
        }
        if (eventRequest.title() != null) {
            findEvent.setTitle(eventRequest.title());
        }

        if (eventRequest.content() != null) {
            findEvent.setContent(eventRequest.content());
        }

        if (eventRequest.zipCode() != null) {
            findEvent.setZipCode(eventRequest.zipCode());
        }

        if (eventRequest.streetAddress() != null) {
            findEvent.setStreetAddress(eventRequest.streetAddress());
        }

        if (eventRequest.detailAddress() != null) {
            findEvent.setDetailAddress(eventRequest.detailAddress());
        }

        if (eventRequest.startAt() != null) {
            findEvent.setStartAt(eventRequest.startAt());
        }

        if (eventRequest.endAt() != null) {
            findEvent.setEndAt(eventRequest.endAt());
        }
        return eventRepository.save(findEvent);
    }

    /**
     * 이벤트 삭제
     *
     * @param id 이벤트 UID
     */
    public void deleteEvent(Long id, String managerEmail) {
        Event event = eventRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 이벤트가 존재하지 않습니다.")
        );
        if (!event.getManager().getEmail().equals(managerEmail)) {
            throw new IllegalArgumentException("해당 이벤트의 매니저가 아닙니다.");
        }
        eventRepository.deleteById(id);
    }

    /**
     * 이벤트 상태 변경
     *
     * @param id           이벤트 UID
     * @param eventStatus  이벤트 상태
     * @param managerEmail 이벤트 매니저 이메일
     */
    public void changeEventStatus(Long id, EventStatus eventStatus, String managerEmail) {
        Event event = eventRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 이벤트가 존재하지 않습니다.")
        );
        if (!event.getManager().getEmail().equals(managerEmail)) {
            throw new IllegalArgumentException("해당 이벤트의 매니저가 아닙니다.");
        }
        event.setStatus(eventStatus);
        eventRepository.save(event);
    }
}
