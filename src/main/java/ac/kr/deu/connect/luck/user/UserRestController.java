package ac.kr.deu.connect.luck.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
@Tag(name = "User", description = "사용자 관련 API")
public class UserRestController {

    private final UserService userService;


    @DeleteMapping("/{id}")
    @Operation(summary = "사용자 삭제", description = "사용자정보를 삭제합니다.\n푸드트럭 매니저의 경우 푸드트럭 정보또한(후기 포함) 삭제됩니다.")
    public ResponseEntity<String> deleteUser(
            @Parameter(name = "id") @PathVariable("id") Long id
    ) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "사용자 조회", description = "사용자정보를 조회합니다.")
    public ResponseEntity<User> getUser(
            @Parameter(name = "id") @PathVariable("id") Long id
    ) {
        return ResponseEntity.ok(userService.findUserById(id));
    }
}
