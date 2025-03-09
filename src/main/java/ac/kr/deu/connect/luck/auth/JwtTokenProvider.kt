package ac.kr.deu.connect.luck.auth

import ac.kr.deu.connect.luck.common.entity.AuthenticatedUser
import ac.kr.deu.connect.luck.user.entity.UserRole
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.time.Instant
import java.util.Date
import java.util.UUID
import javax.crypto.SecretKey
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component

@Component
@EnableConfigurationProperties(JwtProperties::class)
class JwtTokenProvider(
    private val jwtProperties: JwtProperties
) {
    private val key: SecretKey = Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray())

    fun createToken(
        userId: Long,
        email: String,
        roles: Set<UserRole>,
        expiry: Long? = null
    ): String {
        val now = Instant.now()
        return encode(
            AuthenticatedUser(
                jti = UUID.randomUUID().toString(),
                userId = userId,
                email = email,
                roles = roles,
                issuer = jwtProperties.issuer,
                issuedAt = now,
                expiry = now.plusSeconds(expiry ?: jwtProperties.expiry),
            ),
        )
    }

    private fun encode(user: AuthenticatedUser): String {
        return Jwts.builder()
            .id(user.jti)
            .subject(user.userId.toString())
            .claim("email", user.email)
            .claim("roles", user.roles.joinToString(",") { it.name })
            .issuer(user.issuer)
            .issuedAt(Date.from(user.issuedAt))
            .expiration(Date.from(user.expiry))
            .signWith(key)
            .compact()
    }

    fun decode(token: String): AuthenticatedUser {
        val claims = try {
            Jwts.parser()
                .verifyWith(key)
                .requireIssuer(jwtProperties.issuer)
                .build()
                .parseSignedClaims(token)
                .payload
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid token", e)
        }

        return AuthenticatedUser(
            jti = claims.id,
            userId = claims.subject.toLong(),
            email = claims["email"] as String,
            roles = (claims["roles"] as String).split(",").map { UserRole.valueOf(it) }.toSet(),
            issuer = claims.issuer,
            issuedAt = claims.issuedAt.toInstant(),
            expiry = claims.expiration.toInstant(),
        )
    }
}
