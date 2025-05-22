package ac.kr.deu.connect.luck.auth.controller

import ac.kr.deu.connect.luck.auth.controller.request.LoginRequest
import ac.kr.deu.connect.luck.auth.controller.request.SignupRequest
import ac.kr.deu.connect.luck.auth.service.AuthService
import ac.kr.deu.connect.luck.auth.TokenResolver.ACCESS_TOKEN_COOKIE
import ac.kr.deu.connect.luck.auth.TokenResolver.AUTHORIZATION_HEADER
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    @GetMapping("/login")
    fun login(model: Model): String {
        model.addAttribute("loginRequest", LoginRequest("", ""))
        return "auth/login"
    }

    @PostMapping("/login")
    fun loginPost(
        loginRequest: LoginRequest,
        response: HttpServletResponse
    ): String {
        // 로그인
        val token = authService.login(
            email = loginRequest.email,
            password = loginRequest.password
        )

        // 쿠키에 토큰 저장
        val cookie = Cookie(ACCESS_TOKEN_COOKIE, token)
        cookie.path = "/"
        response.addCookie(cookie)

        // 홈 화면으로 리다이렉트
        return "redirect:/"
    }

    @GetMapping("/agreement")
    fun agreement(): String = "auth/agreement"

    @GetMapping("/signup")
    fun signup(model: Model): String {
        model.addAttribute("signUpRequest", SignupRequest("", "", "", ""))
        return "auth/signup"
    }

    /**
     * 회원가입 처리
     *
     * @param signUpRequest 회원가입 정보
     * @param response HttpServletResponse
     * @return 홈 화면
     */
    @PostMapping("/signup")
    fun signupPost(
        signUpRequest: SignupRequest,
        httpServletResponse: HttpServletResponse
    ): String {
        // 회원가입 후 자동 로그인
        // 쿠키 저장 시 토큰을 저장
        httpServletResponse.addCookie(
            Cookie(AUTHORIZATION_HEADER, authService.signup(signUpRequest)).apply {
                maxAge = 60 * 30 // 30분
                path = "/"
            }
        )
        return "redirect:/"
    }

    @GetMapping("/logout")
    fun logout(
        httpServletResponse: HttpServletResponse
    ): String {
        // 쿠키 삭제
        httpServletResponse.addCookie(
            Cookie(ACCESS_TOKEN_COOKIE, "").apply {
                maxAge = 0
                path = "/"
            }
        )

        return "redirect:/"
    }

    @GetMapping("/find-id")
    fun requestIdSearchPage(): String {
        return "auth/find-id"
    }

    @PostMapping("/find-id")
    fun idSearch(@RequestParam("phone") phone: String, model: Model): String {
        model.addAttribute("email", authService.findEmailByPhone(phone))
        return "auth/found-id"
    }
}
