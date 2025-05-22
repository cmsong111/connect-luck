package ac.kr.deu.connect.luck.user.controller.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "비밀번호 변경 폼")
data class PasswordChangeForm(
    @field:Schema(description = "현재 비밀번호", example = "currentPassword")
    val currentPassword: String,
    @field:Schema(description = "새 비밀번호", example = "newPassword")
    val newPassword: String,
)
