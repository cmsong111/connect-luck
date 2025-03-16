package ac.kr.deu.connect.luck.common.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CustomRestExceptionHandler {
    @ExceptionHandler(CustomException::class)
    fun handleBaseException(e: CustomException): ResponseEntity<CustomErrorResponse> {
        return ResponseEntity.status(e.customErrorCode.status)
            .body<CustomErrorResponse>(
                CustomErrorResponse(
                    status = e.customErrorCode.status,
                    errorCode = e.customErrorCode,
                    message = e.customErrorCode.message
                )
            )
    }
}
