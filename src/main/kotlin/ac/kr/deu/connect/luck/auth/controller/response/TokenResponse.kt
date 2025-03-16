package ac.kr.deu.connect.luck.auth.controller.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "토큰 응답 DTO")
data class TokenResponse(
    @field:Schema(description = "엑세스 토큰", example = "eyJhbGciOiJIUzI1NiJ...")
    val token: String
)
