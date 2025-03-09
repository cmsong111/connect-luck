package ac.kr.deu.connect.luck.user.entity

import ac.kr.deu.connect.luck.event.Event
import ac.kr.deu.connect.luck.food_truck.entity.FoodTruck
import ac.kr.deu.connect.luck.food_truck.entity.FoodTruckReview
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.CascadeType
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
import jakarta.persistence.OneToMany
import java.time.Instant
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
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

    @OneToMany(mappedBy = "author", cascade = [CascadeType.REMOVE])
    @JsonManagedReference
    var reviews: MutableList<FoodTruckReview> = mutableListOf(),

    @OneToMany(mappedBy = "manager", cascade = [CascadeType.REMOVE])
    @JsonManagedReference
    var foodTrucks: MutableList<FoodTruck> = mutableListOf(),

    @OneToMany(mappedBy = "manager", cascade = [CascadeType.REMOVE])
    @JsonManagedReference
    var events: MutableList<Event> = mutableListOf(),

    @CreatedDate
    val createdAt: Instant = Instant.now(),

    @LastModifiedDate
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
            )
        }
    }
}
