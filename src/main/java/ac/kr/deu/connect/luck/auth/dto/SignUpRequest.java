package ac.kr.deu.connect.luck.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원가입 요청 정보 객체")
public record SignUpRequest(
        @Schema(description = "이메일", example = "test@test.com")
        String email,
        @Schema(description = "비밀번호", example = "test")
        String password,
        @Schema(description = "이름", example = "홍길동")
        String name,
        @Schema(description = "휴대폰 번호", example = "010-1234-5678")
        String phoneNumber
) {
}
