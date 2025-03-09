package ac.kr.deu.connect.luck.auth.filter

import ac.kr.deu.connect.luck.auth.JwtTokenProvider
import ac.kr.deu.connect.luck.common.entity.AuthenticatedUser
import ac.kr.deu.connect.luck.common.utlis.TokenResolver
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
                return
            }
            val authentication = UsernamePasswordAuthenticationToken(authenticatedUser, token, authenticatedUser.roles)
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)
    }
}
