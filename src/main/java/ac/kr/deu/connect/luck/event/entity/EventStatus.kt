package ac.kr.deu.connect.luck.event.entity

enum class EventStatus {
    /** 행사 시작 전 */
    BEFORE_APPLICATION,

    /** 푸드트럭 모집 중 */
    OPEN_FOR_APPLICATION,

    /** 푸드트럭 모집 완료 */
    APPLICATION_FINISHED,

    /** 행사 진행 중 */
    EVENT_START,

    /** 행사 끝 */
    EVENT_END,

    /** 행사 취소 */
    EVENT_CANCELED,
}
