package ac.kr.deu.connect.luck.exception;

import org.springframework.http.HttpStatus;

public record CustomErrorResponse(
        HttpStatus status,
        CustomErrorCode errorType,
        String message
) {
}
