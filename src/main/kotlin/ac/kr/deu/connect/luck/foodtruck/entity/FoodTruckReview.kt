package ac.kr.deu.connect.luck.foodtruck.entity

import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OrderColumn
import java.time.Instant
import org.hibernate.annotations.SoftDelete

@Entity
@SoftDelete(columnName = "is_deleted")
class FoodTruckReview(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val authorId: Long,

    @Column(columnDefinition = "TEXT")
    var content: String,
    @Column(columnDefinition = "INT CHECK (rating >= 1 AND rating <= 5)")
    var rating: Int,

    @OrderColumn(name = "order_index")
    @ElementCollection(fetch = FetchType.EAGER)
    var images: List<String>? = listOf(),

    val foodTruckId: Long,
    var reply: String? = null,

    val createdAt: Instant = Instant.now(),
    var updatedAt: Instant = Instant.now(),
) {
    companion object {
        fun create(
            foodTruckId: Long,
            authorId: Long,
            content: String,
            images: List<String>?,
            rating: Int,
        ): FoodTruckReview {
            return FoodTruckReview(
                authorId = authorId,
                content = content,
                images = images,
                rating = rating,
                foodTruckId = foodTruckId,
            )
        }
    }
}
