package ac.kr.deu.connect.luck.event.entity

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
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@EntityListeners(AuditingEntityListener::class)
class Event(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    var title: String,
    @Column(columnDefinition = "TEXT")
    var thumbnail: String,
    @Column(columnDefinition = "LONGTEXT")
    var content: String,
    @ElementCollection(fetch = FetchType.LAZY)
    var images: List<String> = emptyList(),
    @Enumerated(EnumType.STRING)
    var status: EventStatus,

    var startDate: Instant? = null,
    var endDate: Instant? = null,

    var zipCode: String? = null,
    var address: String? = null,
    var detailAddress: String? = null,
    var trafficInfo: String? = null,


    val managerId: Long,
    @Column(unique = true)
    val provider: EventProvider? = null,

    @CreatedDate
    val createdAt: Instant = Instant.now(),
    @LastModifiedDate
    val updatedAt: Instant = Instant.now(),
)
