package ac.kr.deu.connect.luck.user.controller.request

data class PasswordChangeForm(
    val currentPassword: String,
    val newPassword: String,
    val newPasswordConfirm: String,
)
