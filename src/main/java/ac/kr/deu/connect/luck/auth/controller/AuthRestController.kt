package ac.kr.deu.connect.luck.auth.controller

import ac.kr.deu.connect.luck.auth.controller.request.LoginRequest
import ac.kr.deu.connect.luck.auth.controller.request.SignupRequest
import ac.kr.deu.connect.luck.auth.controller.response.EmailResponse
import ac.kr.deu.connect.luck.auth.controller.response.IdCheckResponse
import ac.kr.deu.connect.luck.auth.controller.response.TokenResponse
import ac.kr.deu.connect.luck.auth.service.AuthService
import ac.kr.deu.connect.luck.configuration.SpringDocConfig.Companion.AUTH
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(
    name = AUTH,
    description = "인증 관련 API - <b>Auth API 호출 시 Authorization header 부분이 비어 있어야 합니다.</b>"
)
@RestController
@RequestMapping("/api/v1/auth")
class AuthRestController(
    private val authService: AuthService,
) {
    @PostMapping("/login")
    @Operation(summary = "로그인")
    fun login(
        @RequestBody loginRequest: LoginRequest
    ): ResponseEntity<TokenResponse> {
        return ResponseEntity.ok(
            TokenResponse(
                authService.login(
                    loginRequest.email,
                    loginRequest.password
                )
            )
        )
    }

    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    fun signup(
        @RequestBody signupRequest: SignupRequest
    ): ResponseEntity<TokenResponse> {
        return ResponseEntity.ok(
            TokenResponse(
                authService.signup(
                    signupRequest = signupRequest
                )
            )
        )
    }

    @PostMapping("/email-check")
    @Operation(
        summary = "이메일 사용 가능 여부 확인",
        description = "true: 사용 가능, false: 사용 불가"
    )
    fun emailCheck(
        @RequestParam @Parameter(description = "이메일") email: String
    ): ResponseEntity<IdCheckResponse> {
        return ResponseEntity.ok(
            IdCheckResponse(
                authService.checkEmailDuplication(email)
            )
        )
    }

    @PostMapping("/find-email")
    @Operation(summary = "이메일 찾기")
    fun findEmail(
        @RequestParam @Parameter(description = "휴대폰 번호") phone: String
    ): ResponseEntity<EmailResponse> {
        return ResponseEntity.ok(
            EmailResponse.of(
                authService.findEmailByPhone(phone)
            )
        )
    }
}
