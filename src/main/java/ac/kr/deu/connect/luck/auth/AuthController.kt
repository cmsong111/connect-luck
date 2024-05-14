package ac.kr.deu.connect.luck.auth

import ac.kr.deu.connect.luck.auth.dto.LoginRequest
import ac.kr.deu.connect.luck.auth.dto.SignUpRequest
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Controller
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    @Value("\${security.jwt.token.header}")
    lateinit var AUTHORIZATION_HEADER: String

    @Value("\${security.jwt.token.prefix}")
    lateinit var TOKEN_PREFIX: String

    @GetMapping("/login")
    fun login(model: Model): String {
        model.addAttribute("loginRequest", LoginRequest("", ""))
        return "auth/login"
    }

    @PostMapping("/login")
    fun loginPost(
        loginRequest: LoginRequest?,
        response: HttpServletResponse
    ): String {
        // 로그인
        val token = authService.login(loginRequest).token
        val encodedToken = URLEncoder.encode(TOKEN_PREFIX + token, StandardCharsets.UTF_8)

        // 쿠키에 토큰 저장
        val cookie = Cookie(AUTHORIZATION_HEADER, encodedToken)
        cookie.path = "/"
        response.addCookie(cookie)

        // 홈 화면으로 리다이렉트
        return "redirect:/"
    }

    @GetMapping("/agreement")
    fun agreement(): String {
        return "auth/agreement"
    }

    @GetMapping("/signup")
    fun signup(model: Model): String {
        model.addAttribute("signUpRequest", SignUpRequest("", "", "", ""))
        return "auth/signup"
    }

    /**
     * 회원가입 처리
     *
     * @param email    이메일
     * @param password 비밀번호
     * @param name     이름
     * @param
     * @return 홈 화면
     */
    @PostMapping("/signup")
    fun signupPost(
        signUpRequest: SignUpRequest,
        response: HttpServletResponse
    ): String {
        // 회원가입 후 자동 로그인
        // 쿠키 저장 시 토큰을 URL 인코딩(URLEncoder.encode)하여 저장
        val token = authService.signUp(signUpRequest).token
        val encodedToken = URLEncoder.encode(TOKEN_PREFIX + token, StandardCharsets.UTF_8)
        val cookie = Cookie(AUTHORIZATION_HEADER, encodedToken)
        cookie.maxAge = 60 * 30 // 30분
        cookie.path = "/"
        response.addCookie(cookie)

        return "redirect:/"
    }

    @GetMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    fun logout(
        @CookieValue(value = "Authorization", defaultValue = "", required = false) jwtCookie: Cookie,
        httpServletResponse: HttpServletResponse
    ): String {
        // 쿠키 삭제
        jwtCookie.maxAge = 0
        jwtCookie.path = "/"
        jwtCookie.value = null
        httpServletResponse.addCookie(jwtCookie)
        return "redirect:/"
    }

    @GetMapping("/find-id")
    fun requestIdSearchPage(): String {
        return "auth/find-id"
    }

    @PostMapping("/find-id")
    fun idSearch(@RequestParam("phone") phone: String?, model: Model): String {
        model.addAttribute("email", authService.findEmailByPhone(phone))
        return "auth/found-id"
    }

}
