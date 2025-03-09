package ac.kr.deu.connect.luck.configuration

import ac.kr.deu.connect.luck.auth.JwtTokenProvider
import ac.kr.deu.connect.luck.auth.filter.JwtTokenFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableMethodSecurity(securedEnabled = true)
class SecurityConfig(
    private val userDetailsService: UserDetailsService,
    private val jwtTokenProvider: JwtTokenProvider,
) {
    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .headers { it.disable() }
            .sessionManagement { it.disable() }
            .authorizeHttpRequests {
                it
                    .anyRequest().permitAll()
            }
            .userDetailsService(userDetailsService)
            .addFilterBefore(JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }
}
