package ac.kr.deu.connect.luck.event.application.entity

enum class ApplicationStatus {
    /** 요청 중 */
    PENDING,

    /** 승인됨 */
    APPROVED,

    /** 거절됨 */
    REJECTED,

    /** 취소됨 */
    CANCELED,
}
