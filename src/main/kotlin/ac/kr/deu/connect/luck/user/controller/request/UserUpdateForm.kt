package ac.kr.deu.connect.luck.user.controller.request

import ac.kr.deu.connect.luck.user.entity.User
import ac.kr.deu.connect.luck.user.entity.UserRole
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "유저 정보 수정 폼")
data class UserUpdateForm(
    @field:Schema(description = "이름", example = "홍길동")
    val name: String?,
    @field:Schema(description = "전화번호", example = "010-1234-5678")
    val phone: String?,
    @field:Schema(description = "역할")
    val roles: List<UserRole> = listOf(UserRole.USER),
) {
    companion object {
        fun from(user: User): UserUpdateForm {
            return UserUpdateForm(
                name = user.name,
                phone = user.phone,
                roles = user.roles.toList()
            )
        }
    }
}
