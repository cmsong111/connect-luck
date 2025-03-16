package ac.kr.deu.connect.luck.user.entity

import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.Instant
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity(name = "users")
@EntityListeners(AuditingEntityListener::class)
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    @Column(unique = true)
    var email: String,
    @Column
    var password: String,
    @Column
    var name: String,
    @Column
    var phone: String,
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    val roles: MutableSet<UserRole> = mutableSetOf(UserRole.USER),

    @Column(columnDefinition = "text")
    var profileImage: String? = null,

    val createdAt: Instant = Instant.now(),

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
                profileImage = "https://picsum.photos/id/12/200"
            )
        }
    }

    fun update(
        name: String? = null,
        phone: String? = null,
        profileImage: String? = null,
        roles: Set<UserRole> = setOf(),
    ) {
        name?.let { this.name = it }
        phone?.let { this.phone = it }
        profileImage?.let { this.profileImage = it }
        roles.let { this.roles.addAll(it) }
    }
}
