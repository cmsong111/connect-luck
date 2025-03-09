package ac.kr.deu.connect.luck.auth.controller.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "로그인 요청 DTO")
data class LoginRequest(
    @field:Schema(description = "이메일", example = "test1@test.com")
    val email: String,
    @field:Schema(description = "비밀번호", example = "test1")
    val password: String,
)
