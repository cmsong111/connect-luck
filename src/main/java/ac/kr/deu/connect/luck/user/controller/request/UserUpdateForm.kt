package ac.kr.deu.connect.luck.user.controller.request

import ac.kr.deu.connect.luck.user.entity.User

data class UserUpdateForm(
    val name: String?,
    val phone: String?,
) {
    companion object {
        fun from(user: User): UserUpdateForm {
            return UserUpdateForm(
                name = user.name,
                phone = user.phone,
            )
        }
    }
}
