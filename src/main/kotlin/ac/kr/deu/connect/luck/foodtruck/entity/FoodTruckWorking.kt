package ac.kr.deu.connect.luck.foodtruck.entity

import ac.kr.deu.connect.luck.foodtruck.entity.FoodTruckWorking.Status.CLOSED
import ac.kr.deu.connect.luck.foodtruck.entity.FoodTruckWorking.Status.OPEN
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Embeddable
data class FoodTruckWorking(
    @Enumerated(EnumType.STRING)
    var status: Status = Status.OPEN,

    /** 공간 데이터 타입 사용 */
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
) {
    /**
     * 영업 상태
     * @property OPEN 영업중
     * @property CLOSED 영업 종료
     */
    enum class Status {
        OPEN,
        CLOSED,
    }
}
