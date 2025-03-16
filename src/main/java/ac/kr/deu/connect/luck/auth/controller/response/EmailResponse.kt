package ac.kr.deu.connect.luck.auth.controller.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "이메일 검색 응답")
data class EmailResponse(
    @field:Schema(
        description = "이메일 (마지막 4자리는 *로 가려짐)",
        example = "test@tes****"
    )
    val email: String
) {
    companion object {
        fun of(email: String): EmailResponse {
            val index: Int = email.length - 4
            return EmailResponse(
                email = email.replaceRange(index, email.length, "****")
            )
        }
    }
}
