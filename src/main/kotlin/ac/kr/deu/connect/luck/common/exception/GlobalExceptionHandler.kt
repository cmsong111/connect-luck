package ac.kr.deu.connect.luck.common.exception

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleDomainException(e: BadRequestException): ResponseEntity<Any> {
        logger.error { e.message }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
    }

    @ExceptionHandler(UnauthorizedException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleDomainException(e: UnauthorizedException): ResponseEntity<Any> {
        logger.error { e.message }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.message)
    }

    @ExceptionHandler(ForbiddenException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleDomainException(e: ForbiddenException): ResponseEntity<Any> {
        logger.error { e.message }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.message)
    }

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleDomainException(e: NotFoundException): ResponseEntity<Any> {
        logger.error { e.message }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
    }

    @ExceptionHandler(ConflictException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleDomainException(e: ConflictException): ResponseEntity<Any> {
        logger.error { e.message }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.message)
    }

    companion object {
        val logger = KotlinLogging.logger {}
    }
}
