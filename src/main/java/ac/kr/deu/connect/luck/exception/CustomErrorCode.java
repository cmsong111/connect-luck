package ac.kr.deu.connect.luck.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {
    ALREADY_EXIST_USER_ID(HttpStatus.BAD_REQUEST, "이미 존재하는 아이디입니다."),
    EMAIL_NOT_FOUND(HttpStatus.BAD_REQUEST, "이메일을 찾을 수 없습니다."),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    PHONE_NOT_FOUND(HttpStatus.BAD_REQUEST, "휴대폰 번호를 찾을 수 없습니다."),
    ID_NOT_MATCH(HttpStatus.BAD_REQUEST, "유저 번호가 일치하지 않습니다."),
    FOOD_TRUCK_NOT_FOUND(HttpStatus.BAD_REQUEST, "푸드트럭을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
