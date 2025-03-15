package ac.kr.deu.connect.luck.foodtruck.working;

import ac.kr.deu.connect.luck.common.entity.AuthenticatedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "08-Now API", description = "현재 운영중인 푸드트럭 API")
@RestController
@RequestMapping("/api/now")
@RequiredArgsConstructor
public class NowRestController {

    private final NowService nowService;

    @GetMapping
    @Operation(summary = "현재 운영중인 푸드트럭 조회", description = "위도, 경도 입력 시 근처 푸드트럭을 조회합니다. 미 입력 시 운영중인 모든 푸드트럭을 조회합니다.")
    public ResponseEntity<List<Now>> getNow(
            @Parameter(description = "위도") @RequestParam(value = "latitude", required = false) Double latitude,
            @Parameter(description = "경도") @RequestParam(value = "longitude", required = false) Double longitude
    ) {
        return ResponseEntity.ok(nowService.getNow(latitude, longitude));
    }


    @PostMapping("/start/{foodTruckId}")
    @Operation(summary = "푸드트럭 운영 시작", description = "위도, 경도 입력 시 해당 위치에서 운영을 시작합니다. 푸드트럭 매니저의 권한이 필요합니다.")
    @PreAuthorize("hasRole(ROLE_FOOD_TRUCK_MANAGER)")
    public ResponseEntity<Now> startWorking(
            @Parameter(description = "푸드트럭 UID") @PathVariable("foodTruckId") Long foodTruckId,
            @Parameter(description = "위도") @RequestParam(value = "latitude") Double latitude,
            @Parameter(description = "경도") @RequestParam(value = "longitude") Double longitude,
            @AuthenticationPrincipal AuthenticatedUser user) {
        return ResponseEntity.ok(nowService.saveStartNow(foodTruckId, user.getEmail(), latitude, longitude));
    }

    @PreAuthorize("hasRole(ROLE_FOOD_TRUCK_MANAGER)")
    @PostMapping("/end/{foodTruckId}")
    @Operation(summary = "푸드트럭 운영 종료", description = "푸드트럭 운영을 종료합니다. 푸드트럭 매니저의 권한이 필요합니다.")
    public ResponseEntity<Now> stopWorking(
            @Parameter(description = "푸드트럭 UID") @PathVariable("foodTruckId") Long foodTruckId,
            @AuthenticationPrincipal AuthenticatedUser user) {
        return ResponseEntity.ok(nowService.saveEndNow(foodTruckId, user.getEmail()));
    }
}
