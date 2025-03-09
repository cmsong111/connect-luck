package ac.kr.deu.connect.luck.auth

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    val secret: String,
    val issuer: String,
    val expiry: Long,
)
