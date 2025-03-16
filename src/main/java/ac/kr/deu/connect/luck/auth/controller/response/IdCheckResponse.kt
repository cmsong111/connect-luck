package ac.kr.deu.connect.luck.auth.controller.response

import io.swagger.v3.oas.annotations.media.Schema

/**
 * 아이디 중복체크 응답
 */
@Schema(description = "아이디 중복체크 응답")
data class IdCheckResponse(
    @field:Schema(description = "아이디 사용가능 여부 (true: 사용가능, false: 사용불가능)")
    val isAvailable: Boolean,
)
