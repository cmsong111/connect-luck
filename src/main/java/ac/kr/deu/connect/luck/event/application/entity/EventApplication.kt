package ac.kr.deu.connect.luck.event.application.entity

import ac.kr.deu.connect.luck.common.entity.BaseEntity
import jakarta.persistence.Embeddable
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Entity
class EventApplication(
    @EmbeddedId
    val id: ApplicationId,

    @Enumerated(EnumType.STRING)
    private val status: ApplicationStatus = ApplicationStatus.PENDING,

    private val comment: String? = null,
) : BaseEntity() {
    @Embeddable
    data class ApplicationId(
        val eventId: Long,
        val foodTruckId: Long,
    )
}
