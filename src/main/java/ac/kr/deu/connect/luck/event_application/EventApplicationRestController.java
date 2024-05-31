package ac.kr.deu.connect.luck.event_application;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Tag(name = "07-EventApplication", description = "이벤트 신청 관련 API")
@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class EventApplicationRestController {

    private final EventApplicationService eventApplicationService;

    @GetMapping("/application/truck-manager")
    @PreAuthorize("hasRole('ROLE_FOOD_TRUCK_MANAGER')")
    @Operation(summary = "내가 신청한 이벤트 목록 조회", description = "내가 신청한 이벤트 목록을 조회합니다. truck 미 기입 시 나의 모든 신청서<br>푸드트럭 매니저만 사용 가능합니다")
    public List<EventApplication> getMyEventApplicationsForTruckManager(
            Principal principal) {
        return eventApplicationService.getMyEventApplicationsForTruckManager(principal.getName());
    }

    @GetMapping("/application/event-manager")
    @PreAuthorize("hasRole('ROLE_EVENT_MANAGER')")
    @Operation(summary = "내 이벤트에 대한 신청 목록 조회", description = "내 이벤트에 대한 신청 목록을 조회합니다.<br>이벤트 관리자만 사용 가능합니다")
    public List<EventApplication> getMyEventApplicationsForEventManager(
            @Parameter(description = "이벤트 UID") @RequestParam(required = false) Long eventId,
            Principal principal) {
        return eventApplicationService.getMyEventApplicationsForEventManager(eventId, principal.getName());
    }

    //서비스 로직 수정했는데 Rest 로직은 수정 못하겠음 남주 SOS 바람
    @PostMapping("/application")
    @PreAuthorize("hasRole('ROLE_FOOD_TRUCK_MANAGER')")
    @Operation(summary = "이벤트 신청", description = "푸드트럭 매니저가 이벤트에 신청하는 API<br>푸드트럭 매니저만 사용 가능합니다")
    public ResponseEntity<EventApplication> applyEvent(
            @RequestBody EventApplicationRequest eventApplicationRequest,
            Principal principal) {
        return ResponseEntity.ok(eventApplicationService.createEventApplication(eventApplicationRequest, principal.getName()));
    }


    @PostMapping("/event/{eventId}/applications/{applicationId}")
    @PreAuthorize("hasRole('ROLE_EVENT_MANAGER')")
    @Operation(summary = "특정 이벤트에 대한 푸드트럭 신청 승인", description = "특정 이벤트에 대한 푸드트럭 신청을 승인합니다.<br> <b>이벤트 관리자</b>만 승인 가능합니다.")
    public ResponseEntity<EventApplication> approveEventApplication(
            @Parameter(description = "이벤트 UID") @PathVariable(name = "eventId") Long eventId,
            @Parameter(description = "신청 UID") @PathVariable(name = "applicationId") Long applicationId,
            @Parameter(description = "승인 여부") @RequestParam ApplicationStatus applicationStatus) {

        return ResponseEntity.ok(eventApplicationService.approveEventApplication(eventId, applicationId, applicationStatus));
    }
}
