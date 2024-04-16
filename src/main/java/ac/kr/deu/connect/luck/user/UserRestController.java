package ac.kr.deu.connect.luck.user;

import ac.kr.deu.connect.luck.auth.SignUpRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
@Tag(name = "02-User", description = "사용자 관련 API")
public class UserRestController {

    private final UserService userService;


    @DeleteMapping
    @Operation(summary = "사용자 삭제", description = "사용자정보를 삭제합니다.\n푸드트럭 매니저의 경우 푸드트럭 정보또한(후기 포함) 삭제됩니다.")
    public ResponseEntity<String> deleteUser(
            Principal principal
    ) {
        return ResponseEntity.ok(userService.deleteUser(principal.getName()));
    }

    @GetMapping
    @Operation(summary = "사용자 조회", description = "<h1>JWT 토큰을 기반으로 로그인 된 유저 정보를 반환합니다.</h1>헤더에 Authorization 키에 Bearer 토큰을 넣어주세요.")
    public ResponseEntity<User> getUser(
            Principal principal
    ) {
        return ResponseEntity.ok(userService.findUserByEmail(principal.getName()));
    }

    @PatchMapping
    @Operation(summary = "사용자 정보 수정", description = "<h1>사용자정보를 수정합니다.</h1>변경하지 않을 정보는 null로 입력하세요.<br><br><h3>주의: 사용자 정보 변경 시 JWT 토큰을 다시 발급 받으세요.</h3>이전 토큰을 사용하면 요청이 정상적으로 처리되지 않습니다.")
    public ResponseEntity<User> updateUser(
            Principal principal,
            @RequestBody SignUpRequest user
    ) {
        return ResponseEntity.ok(userService.updateUser(principal.getName(), user));
    }

    @PostMapping("/add-role")
    @Operation(summary = "사용자 권한 설정", description = "<h1>사용자의 권한을 추가 합니다.</h1>필요한 권한을 추가 합니다.")
    public ResponseEntity<User> setUserRole(
            Principal principal,
            @Parameter(description = "추가할 권한") @RequestParam("role") UserRole role
    ) {
        return ResponseEntity.ok(userService.setUserRole(principal.getName(), role));
    }

}
