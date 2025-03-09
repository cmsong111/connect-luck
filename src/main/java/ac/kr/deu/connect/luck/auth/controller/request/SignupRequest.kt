package ac.kr.deu.connect.luck.auth.controller.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Pattern

@Schema(description = "회원가입 요청 폼")
class SignupRequest(
    @field:Email
    @field:Schema(description = "이메일", example = "test@example.com")
    val email: String,
    @field:Schema(description = "비밀번호", example = "password")
    val password: String,
    @field:Schema(description = "이름", example = "홍길동")
    val name: String,
    @field:Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}\$")
    @field:Schema(description = "전화번호", example = "010-1234-5678")
    val phoneNumber: String,
)
