package ac.kr.deu.connect.luck.foodtruck.entity

import ac.kr.deu.connect.luck.foodtruck.entity.FoodTruckWorking.Status.FINISHED
import ac.kr.deu.connect.luck.foodtruck.entity.FoodTruckWorking.Status.WORKING
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Embeddable
data class FoodTruckWorking(
    @Enumerated(EnumType.STRING)
    var status: Status = Status.WORKING,

    /** 공간 데이터 타입 사용 */
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
) {
    /**
     * 영업 상태
     * @property WORKING 영업중
     * @property FINISHED 영업 종료
     */
    enum class Status {
        WORKING,
        FINISHED,
    }
}
