package ac.kr.deu.connect.luck.now;

import ac.kr.deu.connect.luck.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Now API", description = "현재 운영중인 푸드트럭 API")
@RestController
@RequestMapping("/api/now")
@RequiredArgsConstructor
public class NowController {

    private final NowService nowService;

    @GetMapping
    @Operation(summary = "현재 운영중인 푸드트럭 조회", description = "위도, 경도 입력 시 근처 푸드트럭을 조회합니다.")
    public ResponseEntity<List<Now>> getNow(
            @RequestParam(value = "latitude", required = false) Double latitude,
            @RequestParam(value = "longitude", required = false) Double longitude
    ) {
        return ResponseEntity.ok(nowService.getNow(latitude, longitude));
    }


    @PostMapping("/start/{foodTruckId}")
    @Operation(summary = "푸드트럭 운영 시작", description = "위도, 경도 입력 시 해당 위치에서 운영을 시작합니다. 푸드트럭 매니저의 권한이 필요합니다.")
    @PreAuthorize("hasRole(ROLE_FOOD_TRUCK_MANAGER)")
    public ResponseEntity<Now> startWorking(
            @PathVariable("foodTruckId") Long foodTruckId,
            @RequestParam(value = "latitude") Double latitude,
            @RequestParam(value = "longitude") Double longitude,
            HttpServletRequest req) {
        return ResponseEntity.ok(nowService.saveStartNow(foodTruckId, req, latitude, longitude));
    }

    @PreAuthorize("hasRole(ROLE_FOOD_TRUCK_MANAGER)")
    @PostMapping("/end/{foodTruckId}")
    @Operation(summary = "푸드트럭 운영 종료")
    public ResponseEntity<Now> stopWorking(
            @Parameter(name = "푸드트럭 IDX") @PathVariable("foodTruckId") Long foodTruckId,
            HttpServletRequest req) {
        return ResponseEntity.ok(nowService.saveEndNow(foodTruckId, req));
    }
}
