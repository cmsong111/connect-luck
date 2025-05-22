package ac.kr.deu.connect.luck.recruitment.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record EventApplicationRequest(
        @Schema(description = "이벤트 ID", example = "1")
        Long eventId,

        @Schema(description = "푸드트럭 ID", example = "2")
        Long foodTruckId,

        @Schema(description = "신청 메시지", example = "죠희 타코야키 잘 만들어요")
        String comment) {
}

