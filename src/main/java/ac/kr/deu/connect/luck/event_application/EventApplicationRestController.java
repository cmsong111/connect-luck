package ac.kr.deu.connect.luck.event_application;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping("/application/my")
    @PreAuthorize("hasRole('ROLE_FOOD_TRUCK_MANAGER')")
    @Operation(summary = "내가 신청한 이벤트 목록 조회 (Not Implement)", description = "내가 신청한 이벤트 목록을 조회합니다.<br>푸드트럭 매니저만 사용 가능합니다")
    public void getMyEventApplications(Principal principal) {
        //TODO: Implement this method
    }

    @PostMapping("/application")
    @PreAuthorize("hasRole('ROLE_FOOD_TRUCK_MANAGER')")
    @Operation(summary = "이벤트 신청", description = "푸드트럭 매니저가 이벤트에 신청하는 API<br>푸드트럭 매니저만 사용 가능합니다")
    public void applyEvent(
            @Parameter(description = "이벤트 UID") @PathVariable(name = "eventId") Long id,
            @RequestBody EventApplicationRequest eventApplicationRequest,
            Principal principal) {
        eventApplicationService.createEventApplication(eventApplicationRequest, id, principal.getName());
    }

    @GetMapping("/event/{id}/applications")
    @PreAuthorize("hasRole('ROLE_EVENT_MANAGER')")
    @Operation(summary = "특정 이벤트에 대한 푸드트럭 신청 목록 조회", description = "특정 이벤트에 대한 푸드트럭 신청 목록을 조회합니다.<br> <b>이벤트 관리자</b>만 조회 가능합니다.")
    public void getEventApplications(
            @Parameter(description = "이벤트 UID") @PathVariable(name = "id") Long id) {
        //TODO: Implement this method
    }

    @PostMapping("/event/{eventId}/applications/{applicationId}")
    @PreAuthorize("hasRole('ROLE_EVENT_MANAGER')")
    @Operation(summary = "특정 이벤트에 대한 푸드트럭 신청 승인", description = "특정 이벤트에 대한 푸드트럭 신청을 승인합니다.<br> <b>이벤트 관리자</b>만 승인 가능합니다.")
    public void approveEventApplication(
            @Parameter(description = "이벤트 UID") @PathVariable(name = "eventId") Long eventId,
            @Parameter(description = "신청 UID") @PathVariable(name = "applicationId") Long applicationId,
            @Parameter(description = "승인 여부") @RequestParam ApplicationStatus applicationStatus) {
        //TODO: Implement this method
    }
}
