package ac.kr.deu.connect.luck.event.entity;

public enum EventStatus {
    BEFORE_APPLICATION,   // 모집 전
    OPEN_FOR_APPLICATION, // 신청 가능
    APPLICATION_FINISHED,  // 신청 만료
    EVENT_START,          // 행사 시작
    EVENT_END             // 행사 끝
}
