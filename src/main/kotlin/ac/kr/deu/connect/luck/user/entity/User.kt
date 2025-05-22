package ac.kr.deu.connect.luck.user.entity

import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.Instant
import org.hibernate.annotations.SoftDelete
import org.springframework.security.crypto.password.PasswordEncoder

@Entity(name = "users")
@SoftDelete(columnName = "is_deleted")
class User(
    /** 유저 ID */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    /** 이메일 (로그인) */
    @Column(unique = true, nullable = false)
    var email: String,
    /** 비밀번호 */
    @Column(nullable = false)
    var password: String,
    /** 이름 */
    @Column(nullable = false)
    var name: String,
    /** 전화번호 */
    @Column
    var phone: String? = null,
    /** 유저 권한 */
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    val roles: MutableSet<UserRole> = mutableSetOf(UserRole.USER),
    /** 프로필 이미지 URL */
    @Column(columnDefinition = "TEXT")
    var profileImageUrl: String? = null,
    /** 생성일 */
    @Column(updatable = false)
    val createdAt: Instant = Instant.now(),
    /** 수정일 */
    @Column
    var updatedAt: Instant = Instant.now(),
) {
    companion object {
        fun create(
            email: String,
            password: String,
            name: String,
            phone: String,
        ): User {
            return User(
                email = email,
                password = password,
                name = name,
                phone = phone,
                profileImageUrl = "https://picsum.photos/id/12/200"
            )
        }
    }

    /**
     * Update user information.
     * @param name New name
     * @param phone New phone number
     * @param profileImage New profile image URL
     * @param roles New user roles
     */
    fun update(
        name: String? = null,
        phone: String? = null,
        profileImage: String? = null,
        roles: Set<UserRole> = setOf(),
    ) {
        name?.let { this.name = it }
        phone?.let { this.phone = it }
        profileImage?.let { this.profileImageUrl = it }
        roles.let { this.roles.addAll(it) }
        updatedAt = Instant.now()
    }

    /**
     * Update user password.
     * @param password New password
     * @param passwordEncoder Password encoder
     */
    fun updatePassword(
        password: String,
        passwordEncoder: PasswordEncoder,
    ) {
        this.password = passwordEncoder.encode(password)
        updatedAt = Instant.now()
    }

    /**
     * Change email to deleted email.
     * @param now Current time
     */
    fun updateEmailToDeletedEmail(
        now: Instant = Instant.now(),
    ) {
        this.email = "${this.email}@deleted-$now"
        this.updatedAt = Instant.now()
    }
}
