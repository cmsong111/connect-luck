package ac.kr.deu.connect.luck.event.entity

import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Embeddable
data class EventProvider(
    @Enumerated(EnumType.STRING)
    val provider: Provider,
    val providerId: String,
)

enum class Provider {
    /** 부산광역시_부산축제정보 서비스(공공 데이터 포털) */
    BUSAN_FESTIVAL_API

}
