package ac.kr.deu.connect.luck.event;

import ac.kr.deu.connect.luck.event_application.ApplicationStatus;
import ac.kr.deu.connect.luck.event_application.EventApplication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event")
@Tag(name = "06-Event", description = "이벤트 관련 API")
@RequiredArgsConstructor
public class EventRestController {

    private final EventService eventService;

    @GetMapping
    @Operation(summary = "이벤트 목록 조회", description = "모든 이벤트 목록 조회")
    public ResponseEntity<List<Event>> getEvent(
            @Parameter(description = "이벤트 상태 조회(Not Implement)") @RequestParam(required = false) EventStatus eventStatus
    ) {
        return ResponseEntity.ok(eventService.getEvents());
    }

    @GetMapping("/{id}")
    @Operation(summary = "이벤트 상세 조회", description = "이벤트 ID로 상세 조회")
    public ResponseEntity<Event> getEvent(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEvent(id));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_EVENT_MANAGER')")
    @Operation(summary = "이벤트 수정", description = "이벤트 수정")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody EventRequest eventRequest) {
        return ResponseEntity.ok(eventService.updateEvent(id, eventRequest));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_EVENT_MANAGER')")
    @Operation(summary = "이벤트 생성", description = "이벤트 생성\n이벤트 주소입력 시 카카오 우편번호 서비스를 사용해서 주소를 입력받아야함.")
    public ResponseEntity<Event> createEvent(@RequestBody EventRequest eventRequest) {
        return ResponseEntity.ok(eventService.createEvent(eventRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_EVENT_MANAGER')")
    @Operation(summary = "이벤트 삭제", description = "이벤트 삭제")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/status")
    @PreAuthorize("hasRole('ROLE_EVENT_MANAGER')")
    @Operation(summary = "이벤트 상태 변경", description = "이벤트 상태 변경")
    public void changeEventStatus(
            @Parameter(description = "이벤트 UID") @PathVariable Long id,
            @Parameter(description = "이벤트 상태") @RequestParam EventStatus eventStatus) {
        //TODO: Implement this method
    }




}
