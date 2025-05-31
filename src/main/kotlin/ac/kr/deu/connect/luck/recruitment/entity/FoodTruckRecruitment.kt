package ac.kr.deu.connect.luck.recruitment.entity

import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OrderColumn
import java.math.BigDecimal
import java.time.Instant
import org.hibernate.annotations.SoftDelete

@Entity
@SoftDelete(columnName = "is_deleted")
class FoodTruckRecruitment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    /** 작성자 ID */
    val authorId: Long,

    /** 제목 */
    var title: String,

    /** 썸네일 */
    @Column(columnDefinition = "text")
    var thumbnailUrl: String,

    /** 사진 */
    @OrderColumn(name = "image_order")
    @ElementCollection(fetch = FetchType.LAZY)
    @Column(name = "image_url", columnDefinition = "text")
    var images: MutableList<String> = mutableListOf(),

    /** 설명 */
    @Column(columnDefinition = "LONGTEXT")
    var description: String,

    /** 행사 장소 */
    var address: String,
    var detailAddress: String? = null,
    var zipCode: String? = null,
    var latitude: Double? = null,
    var longitude: Double? = null,
    var placeName: String,

    /** 모집 상태 */
    @Enumerated(EnumType.STRING)
    var status: RecruitmentStatus = RecruitmentStatus.RECRUITING,

    /** 모집 인원 수 */
    var recruitmentCount: Int,

    /** 자리세 */
    var fee: BigDecimal = BigDecimal.ZERO,
    @Enumerated(EnumType.STRING)
    var feeDuration: FeeDuration = FeeDuration.EVENT,

    /** 모집 일정 */
    var applicationStartDate: Instant,
    var applicationEndDate: Instant,

    /** 영업 일정 */
    var operationStartDate: Instant,
    var operationEndDate: Instant,
    @Column(columnDefinition = "text")
    var opertaionDescription: String? = null,

    /** 문의처 */
    var contact: String? = null,
    var contactNumber: String? = null,
    var contactEmail: String? = null,

    @Column(updatable = false)
    val createdAt: Instant = Instant.now(),
    var updatedAt: Instant = Instant.now(),
)


/**
 * 모집 상태
 */
enum class RecruitmentStatus {
    /** 모집 중 */
    RECRUITING,

    /** 모집 종료 */
    CLOSED,

    /** 모집 취소 */
    CANCELED,

    /** 모집 완료 */
    FINISHED
}

enum class FeeDuration {
    /** 일 단위 */
    DAY,

    /** 주 단위 */
    WEEK,

    /** 월 단위 */
    MONTH,

    /** 행사 기간 */
    EVENT,
}
