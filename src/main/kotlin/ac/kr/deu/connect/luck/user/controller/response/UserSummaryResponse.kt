package ac.kr.deu.connect.luck.user.controller.response

import ac.kr.deu.connect.luck.user.entity.User
import java.time.Instant

class UserSummaryResponse(
    val userId: Long,
    val email: String,
    val name: String,
    val profileImage: String?,
    val createdAt: Instant,
) {
    companion object {
        fun from(user: User): UserSummaryResponse {
            return UserSummaryResponse(
                userId = user.id,
                email = user.email,
                name = user.name,
                profileImage = user.profileImageUrl,
                createdAt = user.createdAt,
            )
        }
    }
}
