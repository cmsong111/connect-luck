package ac.kr.deu.connect.luck.event;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event")
@Tag(name = "Event", description = "이벤트 관련 API")
@RequiredArgsConstructor
public class EventRestController {

    private final EventService eventService;

    @GetMapping
    @Operation(summary = "이벤트 목록 조회", description = "모든 이벤트 목록 조회")
    public ResponseEntity<List<Event>> getEvent() {
        return ResponseEntity.ok(eventService.getEvents());
    }

    @GetMapping("/{id}")
    @Operation(summary = "이벤트 상세 조회", description = "이벤트 ID로 상세 조회")
    public ResponseEntity<Event> getEvent(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEvent(id));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "이벤트 수정", description = "이벤트 수정")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody EventRequest eventRequest) {
        return ResponseEntity.ok(eventService.updateEvent(id, eventRequest));
    }

    @PostMapping
    @Operation(summary = "이벤트 생성", description = "이벤트 생성\n이벤트 주소입력 시 카카오 우편번호 서비스를 사용해서 주소를 입력받아야함.")
    public ResponseEntity<Event> createEvent(@RequestBody EventRequest eventRequest) {
        return ResponseEntity.ok(eventService.createEvent(eventRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "이벤트 삭제", description = "이벤트 삭제")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok().build();
    }
}
