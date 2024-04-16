package ac.kr.deu.connect.luck.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Value("${security.jwt.token.header}")
    private String AUTHORIZATION_HEADER;

    @Value("${security.jwt.token.prefix}")
    private String TOKEN_PREFIX;

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String loginPost(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpServletResponse response) {
        // 로그인
        String token = authService.login(new LoginRequest(email, password));
        String encodedToken = URLEncoder.encode(TOKEN_PREFIX + token, StandardCharsets.UTF_8);

        // 쿠키에 토큰 저장
        Cookie cookie = new Cookie(AUTHORIZATION_HEADER, encodedToken);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/";
    }

    @GetMapping("/agreement")
    public String agreement() {
        return "auth/agreement";
    }

    @GetMapping("/signup")
    public String signup() {
        return "auth/signup";
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
    public String signupPost(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("phone") String phone,
            HttpServletResponse response) {
        // 회원가입 후 자동 로그인
        // 쿠키 저장 시 토큰을 URL 인코딩(URLEncoder.encode)하여 저장
        String token = authService.signUp(new SignUpRequest(email, password, name, phone));
        String encodedToken = URLEncoder.encode(TOKEN_PREFIX + token, StandardCharsets.UTF_8);
        Cookie cookie = new Cookie(AUTHORIZATION_HEADER, encodedToken);
        cookie.setMaxAge(60 * 30); // 30분
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(@CookieValue(value = "Authorization", defaultValue = "", required = false) Cookie jwtCookie,
                         HttpServletResponse httpServletResponse) {
        // 쿠키 삭제
        jwtCookie.setMaxAge(0);
        jwtCookie.setPath("/");
        jwtCookie.setValue(null);
        httpServletResponse.addCookie(jwtCookie);
        return "redirect:/";
    }

    @GetMapping("/find-id")
    public String requestIdSearchPage() {
        return "auth/find-id";
    }

    @PostMapping("/find-id")
    public String idSearch(@RequestParam("phone") String phone, Model model) {
        model.addAttribute("email", authService.findEmailByPhone(phone));
        return "auth/found-id";
    }
}
