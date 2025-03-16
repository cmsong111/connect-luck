package ac.kr.deu.connect.luck.foodtruck.entity

import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OrderColumn
import java.time.Instant
import org.hibernate.annotations.SoftDelete
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@EntityListeners(AuditingEntityListener::class)
@SoftDelete(columnName = "is_deleted")
class FoodTruck(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    var name: String,
    var description: String? = null,
    var thumbnail: String,
    @Enumerated(EnumType.STRING)
    var type: FoodType = FoodType.ETC,
    var managerId: Long,

    @OrderColumn(name = "image_order")
    @ElementCollection(fetch = FetchType.LAZY)
    var images: MutableList<String> = mutableListOf(),

    @OrderColumn(name = "menu_order")
    @ElementCollection(fetch = FetchType.LAZY)
    var menus: MutableList<FoodTruckMenu> = mutableListOf(),

    var working: FoodTruckWorking? = null,

    @CreatedDate
    val createdAt: Instant = Instant.now(),
    @LastModifiedDate
    var updatedAt: Instant = Instant.now(),
) {
    companion object {
        fun create(
            name: String,
            description: String,
            thumbnail: String,
            type: FoodType,
            managerId: Long,
            images: List<String>,
        ): FoodTruck {
            return FoodTruck(
                name = name,
                description = description,
                thumbnail = thumbnail,
                type = type,
                managerId = managerId,
                images = images.toMutableList(),
            )
        }
    }

    fun update(
        name: String? = null,
        description: String? = null,
        thumbnail: String? = null,
        type: FoodType? = null,
        images: List<String>? = null,
    ) {
        name?.let { this.name = it }
        description?.let { this.description = it }
        thumbnail?.let { this.thumbnail = it }
        type?.let { this.type = it }
        images?.let { this.images = it.toMutableList() }
    }
}
