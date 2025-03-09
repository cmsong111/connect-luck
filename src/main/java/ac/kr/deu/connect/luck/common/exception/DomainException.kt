package ac.kr.deu.connect.luck.common.exception

abstract class DomainException(
    val code: String,
    override val message: String,
) : RuntimeException(message)


open class BadRequestException(
    code: String,
    message: String,
) : DomainException(code, message)

open class UnauthorizedException(
    code: String,
    message: String,
) : DomainException(code, message)

open class ForbiddenException(
    code: String,
    message: String,
) : DomainException(code, message)

open class NotFoundException(
    code: String,
    message: String,
) : DomainException(code, message)

open class ConflictException(
    code: String,
    message: String,
) : DomainException(code, message)
