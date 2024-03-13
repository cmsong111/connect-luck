package ac.kr.deu.connect.luck.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {
    private final CustomErrorCode customErrorCode;
}

