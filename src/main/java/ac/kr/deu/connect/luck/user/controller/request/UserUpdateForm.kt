package ac.kr.deu.connect.luck.user.controller.request

import ac.kr.deu.connect.luck.user.entity.User
import ac.kr.deu.connect.luck.user.entity.UserRole

data class UserUpdateForm(
    val name: String?,
    val phone: String?,
    val roles: Set<UserRole> = setOf(UserRole.USER),
) {
    companion object {
        fun from(user: User): UserUpdateForm {
            return UserUpdateForm(
                name = user.name,
                phone = user.phone,
                roles = user.roles
            )
        }
    }
}
