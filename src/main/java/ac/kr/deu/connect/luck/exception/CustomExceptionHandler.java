package ac.kr.deu.connect.luck.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<CustomErrorResponse> handleBaseException(CustomException e) {
        return ResponseEntity.status(e.getCustomErrorCode().getStatus())
                .body(new CustomErrorResponse(e.getCustomErrorCode().getStatus(), e.getCustomErrorCode(), e.getCustomErrorCode().getMessage()));
    }
}

