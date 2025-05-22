package ac.kr.deu.connect.luck.auth.filter

import ac.kr.deu.connect.luck.auth.JwtTokenProvider
import ac.kr.deu.connect.luck.common.controller.data.AuthenticatedUser
import ac.kr.deu.connect.luck.auth.TokenResolver
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtTokenFilter(
    private val tokenProvider: JwtTokenProvider,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        TokenResolver.resolveToken(request)?.let { token ->
            val authenticatedUser: AuthenticatedUser = try {
                tokenProvider.decode(token)
            } catch (e: Exception) {
                log.error(e) { "Failed to decode JWT token" }
                return
            }
            val authentication = UsernamePasswordAuthenticationToken(authenticatedUser, token, authenticatedUser.roles)
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)
    }

    companion object {
        val log = KotlinLogging.logger {}
    }
}
