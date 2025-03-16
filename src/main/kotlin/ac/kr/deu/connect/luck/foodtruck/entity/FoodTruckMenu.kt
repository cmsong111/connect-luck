package ac.kr.deu.connect.luck.foodtruck.entity

import jakarta.persistence.Embeddable
import java.math.BigDecimal

@Embeddable
data class FoodTruckMenu(
    val name: String,
    val description: String?,
    val image: String?,
    val price: BigDecimal?,
)
