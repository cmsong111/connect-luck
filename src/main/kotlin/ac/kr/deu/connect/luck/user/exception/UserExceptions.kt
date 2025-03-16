package ac.kr.deu.connect.luck.user.exception

import ac.kr.deu.connect.luck.common.exception.BadRequestException

class UserExceptions {
}

data class PasswordIncorrectException(
    override val message: String = "Password does not match the password"
) : BadRequestException("PASSWORD_INCORRECT", message)
