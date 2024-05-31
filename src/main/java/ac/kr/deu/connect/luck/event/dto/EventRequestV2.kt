package ac.kr.deu.connect.luck.event.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

@Schema(description = "이벤트 요청 V2")
data class EventRequestV2(
    @field:Schema(description = "제목", example = "이벤트 제목")
    val title: String,
    @field:Schema(description = "내용", example = "이벤트 내용")
    val content: String,
    @field:Schema(description = "우편번호", example = "12345")
    val zipCode: String,
    @field:Schema(description = "도로명 주소", example = "서울시 강남구 테헤란로 123")
    val streetAddress: String,
    @field:Schema(description = "상세 주소", example = "역삼동 123-456")
    val detailAddress: String,
    @field:Schema(description = "이벤트 시작일", example = "2021-01-01T00:00:00Z")
    @field:DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val startAt: LocalDateTime,
    @field:Schema(description = "이벤트 종료일", example = "2021-01-01T23:59:59Z")
    @field:DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val endAt: LocalDateTime,
    @field:Schema(description = "이미지", format = "binary")
    var image: MultipartFile?
) {

    override fun toString(): String {
        return "EventRequestV2(title='$title'," +
                " content='$content', zipCode='$zipCode'," +
                " streetAddress='$streetAddress'," +
                " detailAddress='$detailAddress', startAt=$startAt," +
                " endAt=$endAt, image=${image?.originalFilename})"
    }
}
