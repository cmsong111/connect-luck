package ac.kr.deu.connect.luck.event_application;

import io.swagger.v3.oas.annotations.media.Schema;

public record EventApplicationRequest(
        @Schema(description = "신청 메시지", example = "죠희 타코야키 잘 만들어요")
        String comment) {
}
