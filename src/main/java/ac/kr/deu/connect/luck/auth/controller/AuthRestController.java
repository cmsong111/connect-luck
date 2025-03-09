package ac.kr.deu.connect.luck.auth.controller;

import ac.kr.deu.connect.luck.auth.service.AuthService;
import ac.kr.deu.connect.luck.auth.controller.request.LoginRequest;
import ac.kr.deu.connect.luck.auth.controller.request.SignUpRequest;
import ac.kr.deu.connect.luck.auth.controller.response.EmailResponse;
import ac.kr.deu.connect.luck.auth.controller.response.IdCheckResponse;
import ac.kr.deu.connect.luck.auth.controller.response.TokenResponse;
import ac.kr.deu.connect.luck.exception.CustomErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "01-Auth", description = "인증 관련 API - <b>Auth API 호출 시 Authorization header 부분이 비어 있어야 합니다.</b>")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthRestController {
    private final AuthService authService;

    @PostMapping("/email-check")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "아이디 중복 체크 성공", content = @Content(schema = @Schema(implementation = IdCheckResponse.class)))
    })
    @Operation(summary = "아이디 중복 체크", description = "<h1>아이디 중복 체크를 수행합니다.</h1><h2>반환 값</h2><li>true: 사용가능</li><li>false: 불가능</li>")
    public ResponseEntity<IdCheckResponse> idCheck(
            @Parameter(description = "이메일") @RequestParam("email") String email) {
        return ResponseEntity.ok(authService.idCheck(email));
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "입력된 이메일과 비밀번호를 사용하여 로그인을 진행합니다.<br>로그인 성공 시 JWT 토큰을 반환합니다.<br>인증이 필요한 요청 시 헤더에 Authorization Bear {JWT}를 포함하여 요청을 보내야 합니다.<br><b>주의: Auth 관련 API 호출 시 Header 부분이 비어 있어야 합니다.</b>")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(schema = @Schema(implementation = TokenResponse.class))),
            @ApiResponse(responseCode = "400", description = "로그인 실패", content = @Content(schema = @Schema(implementation = CustomErrorResponse.class)))
    })
    public TokenResponse login(
            @Parameter(description = "로그인 요청 정보") @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "입력된 폼을 바탕으로 로그인을 진행합니다.<br>사용 전 email-check를 통해 이메일이 사용가능 여부를 확인바랍니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공", content = @Content(schema = @Schema(implementation = TokenResponse.class))),
            @ApiResponse(responseCode = "400", description = "회원가입 실패", content = @Content(schema = @Schema(implementation = CustomErrorResponse.class)))
    })
    public TokenResponse signUp(
            @Parameter(name = "로그인 객체", description = "회원가입 요청 정보") @RequestBody SignUpRequest signUpRequest) {
        return authService.signUp(signUpRequest);
    }

    @PostMapping("/findEmailByPhone")
    @Operation(summary = "휴대폰 번호로 이메일 찾기", description = "휴대폰 번호로 가입된 이메일을 찾습니다.")
    public ResponseEntity<EmailResponse> findEmailByPhone(
            @Parameter(description = "전화번호") @RequestParam("phone") String phone) {
        return ResponseEntity.ok(authService.findEmailByPhone(phone));
    }

}
