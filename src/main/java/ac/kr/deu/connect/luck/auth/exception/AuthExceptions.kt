package ac.kr.deu.connect.luck.auth.exception

import ac.kr.deu.connect.luck.common.exception.ConflictException

data class EmailAlreadyExistsException(
    override val message: String = "Email already exists",
) : ConflictException("EMAIL_ALREADY_EXISTS", message)

data class EmailOrPasswordIncorrectException(
    override val message: String = "Email or password is incorrect",
) : ConflictException("EMAIL_OR_PASSWORD_INCORRECT", message)

data class UserNotFoundException(
    override val message: String = "User not found",
) : ConflictException("USER_NOT_FOUND", message)
