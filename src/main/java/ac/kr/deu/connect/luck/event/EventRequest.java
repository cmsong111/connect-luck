package ac.kr.deu.connect.luck.event;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "이벤트 생성 요청 정보")
public record EventRequest(
        @Schema(description = "이벤트 제목", example = "이벤트 제목")
        String title,
        @Schema(description = "이벤트 내용", example = "이벤트 내용")
        String content,
        @Schema(description = "우편번호", example = "12345")
        String zipCode,
        @Schema(description = "도로명 주소", example = "도로명 주소")
        String streetAddress,
        @Schema(description = "상세 주소", example = "상세 주소")
        String detailAddress,
        @Schema(description = "시작 일시", example = "2021-08-01T00:00:00")
        LocalDateTime startAt,
        @Schema(description = "종료 일시", example = "2021-08-01T23:59:59")
        LocalDateTime endAt,
        @Schema(description = "이미지 URL", example = "https://chrisandpartners.co/wp-content/uploads/2021/11/%EA%B7%B8%EB%A6%BC1.jpg")
        String imageUrl,
        @Schema(description = "매니저 ID", example = "1")
        Long managerId
) {
}
