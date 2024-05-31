package ac.kr.deu.connect.luck.event.controller;

import ac.kr.deu.connect.luck.event.Event;
import ac.kr.deu.connect.luck.event.EventService;
import ac.kr.deu.connect.luck.event.EventStatus;
import ac.kr.deu.connect.luck.event.dto.EventDetailResponse;
import ac.kr.deu.connect.luck.event.dto.EventRequestV2;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/event")
@Tag(name = "06-Event", description = "이벤트 관련 API")
@RequiredArgsConstructor
public class EventRestController {

    private final EventService eventService;

    @GetMapping
    @Operation(summary = "이벤트 목록 조회", description = "모든 이벤트 목록 조회")
    public ResponseEntity<List<EventDetailResponse>> getEvent(
            @Parameter(description = "이벤트 상태 조회(Not Implement)") @RequestParam(required = false) EventStatus eventStatus
    ) {
        return ResponseEntity.ok(eventService.getEvents(eventStatus));
    }

    @GetMapping("/{id}")
    @Operation(summary = "이벤트 상세 조회", description = "이벤트 ID로 상세 조회")
    public ResponseEntity<EventDetailResponse> getEvent(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEvent(id));
    }

    /**
     * 이벤트 생성 Rest API
     *
     * @param eventRequest 이벤트 생성 요청 정보
     * @param principal    로그인 한 사용자 정보
     * @return 생성된 이벤트 정보
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_EVENT_MANAGER')")
    @Operation(summary = "이벤트 생성", description = "이벤트 생성\n이벤트 주소입력 시 카카오 우편번호 서비스를 사용해서 주소를 입력받아야함.")
    public ResponseEntity<Event> createEvent(
            @ModelAttribute EventRequestV2 eventRequest,
            Principal principal
    ) {
        return ResponseEntity.ok(eventService.createEvent(eventRequest, principal.getName()));
    }


    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_EVENT_MANAGER')")
    @Operation(summary = "이벤트 수정", description = "이벤트 수정")
    public ResponseEntity<Event> updateEvent(
            @Parameter(description = "이벤트 UID") @PathVariable(value = "id", required = false) Long id,
            @ModelAttribute EventRequestV2 eventRequest,
            Principal principal) {
        return ResponseEntity.ok(eventService.updateEvent(id, eventRequest, principal.getName()));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_EVENT_MANAGER')")
    @Operation(summary = "이벤트 삭제", description = "이벤트 삭제")
    public ResponseEntity<String> deleteEvent(
            @Parameter(description = "이벤트 UID") @PathVariable("id") Long id,
            Principal principal) {
        eventService.deleteEvent(id, principal.getName());
        return ResponseEntity.ok("이벤트 삭제 성공");
    }

    @PostMapping("/{id}/status")
    @PreAuthorize("hasRole('ROLE_EVENT_MANAGER')")
    @Operation(summary = "이벤트 상태 변경", description = "이벤트 상태 변경")
    public ResponseEntity<String> changeEventStatus(
            @Parameter(description = "이벤트 UID") @PathVariable Long id,
            @Parameter(description = "이벤트 상태") @RequestParam EventStatus eventStatus,
            Principal principal) {
        eventService.changeEventStatus(id, eventStatus, principal.getName());
        return ResponseEntity.ok("이벤트 상태 변경 성공");
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('ROLE_EVENT_MANAGER')")
    @Operation(summary = "내 이벤트 목록 조회", description = "내가 생성한 이벤트 목록 조회")
    public ResponseEntity<List<EventDetailResponse>> getMyEvent(Principal principal) {
        return ResponseEntity.ok(eventService.getEvents(principal.getName()));
    }
}
