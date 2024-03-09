package ac.kr.deu.connect.luck.auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그인 요청 정보 객체")
public record LoginRequest(
        @Schema(description = "이메일", example = "test@test.com")
        String email,
        @Schema(description = "비밀번호", example = "test1234")
        String password
) {
}
