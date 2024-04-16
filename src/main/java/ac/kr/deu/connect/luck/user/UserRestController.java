package ac.kr.deu.connect.luck.user;

import ac.kr.deu.connect.luck.auth.SignUpRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @Operation(summary = "사용자 조회", description = "로그인 된 유저 정보를 반환합니다. (JWT 토큰)")
    public ResponseEntity<User> getUser(
            Principal principal
    ) {
        return ResponseEntity.ok(userService.findUserByEmail(principal.getName()));
    }

    @PatchMapping
    @Operation(summary = "사용자 정보 수정", description = "사용자정보를 수정합니다. 수정할 정보만 입력하면 됩니다. 바꾸지 않을 정보는 null로 보내면 됩니다.")
    public ResponseEntity<User> updateUser(
            Principal principal,
            @RequestBody SignUpRequest user
    ) {
        return ResponseEntity.ok(userService.updateUser(principal.getName(), user));
    }

    @PostMapping("/add-role")
    @Operation(summary = "사용자 권한 설정", description = "사용자의 권한을 추가 합니다.")
    public ResponseEntity<User> setUserRole(
            Principal principal,
            @Parameter(name = "role") @RequestParam("role") UserRole role
    ) {
        return ResponseEntity.ok(userService.setUserRole(principal.getName(), role));
    }

}
