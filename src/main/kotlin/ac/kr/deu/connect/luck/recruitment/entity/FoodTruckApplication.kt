package ac.kr.deu.connect.luck.recruitment.entity

import ac.kr.deu.connect.luck.foodtruck.entity.FoodTruck
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.time.Instant

@Entity
class FoodTruckApplication(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    /** 지원자 ID */
    var authorId: Long,

    /** 지원 공고 */
    @ManyToOne(fetch = FetchType.LAZY)
    val recruitment: FoodTruckRecruitment,

    /** 지원한 푸드트럭 ID */
    @ManyToOne(fetch = FetchType.LAZY)
    val foodTruck: FoodTruck,

    /** 지원서 내용 */
    @Column(columnDefinition = "LONGTEXT")
    var content: String,

    /** 지원서 상태 */
    @Column(nullable = false)
    var status: ApplicationStatus = ApplicationStatus.PENDING,

    @Column(updatable = false)
    val createdAt: Instant = Instant.now(),
    var updatedAt: Instant = Instant.now(),
)


enum class ApplicationStatus {
    /** 대기 중 */
    PENDING,

    /** 승인됨 */
    APPROVED,

    /** 거절됨 */
    REJECTED,

    /** 취소됨 (지원자가 스스로) */
    CANCELED
}
