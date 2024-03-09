package ac.kr.deu.connect.luck.auth;

import ac.kr.deu.connect.luck.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "인증 관련 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthRestController {
    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "로그인")
    public User login(
            @Parameter(description = "로그인 요청 정보") @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/signup")
    public User signUp(
            @Parameter(description = "회원가입 요청 정보") @RequestBody SignUpRequest signUpRequest) {
        return authService.signUp(signUpRequest);
    }

}
