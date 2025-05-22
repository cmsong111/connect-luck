package ac.kr.deu.connect.luck.foodtruck.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.math.BigDecimal

@Embeddable
data class FoodTruckMenu(
    @Column(nullable = false)
    val name: String,
    val description: String?,
    @Column(name = "image_url", columnDefinition = "text")
    val imageUrl: String?,
    val price: BigDecimal = BigDecimal.ZERO,
)
