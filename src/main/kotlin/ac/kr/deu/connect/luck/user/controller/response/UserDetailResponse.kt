package ac.kr.deu.connect.luck.user.controller.response

import ac.kr.deu.connect.luck.user.entity.User
import ac.kr.deu.connect.luck.user.entity.UserRole
import java.time.Instant

data class UserDetailResponse(
    val userId: Long,
    val email: String,
    val name: String,
    val phone: String? = null,
    val profileImage: String?,
    val roles: Set<UserRole>,
    val createdAt: Instant,
) {
    companion object {
        fun from(
            user: User
        ): UserDetailResponse {
            return UserDetailResponse(
                userId = user.id,
                email = user.email,
                name = user.name,
                phone = user.phone,
                profileImage = user.profileImageUrl,
                roles = user.roles,
                createdAt = user.createdAt,
            )
        }
    }
}
