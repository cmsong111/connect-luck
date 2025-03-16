package ac.kr.deu.connect.luck.common.exception

import org.springframework.http.HttpStatus

data class CustomErrorResponse (
    val status: HttpStatus,
    val errorCode: CustomErrorCode,
    val message: String
) {
}
