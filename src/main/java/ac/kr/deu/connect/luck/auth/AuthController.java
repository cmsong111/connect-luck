package ac.kr.deu.connect.luck.auth;

import ac.kr.deu.connect.luck.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String loginPost(String email, String password, HttpServletRequest httpServletRequest, Model model) {
        // 세션 초기화
        httpServletRequest.getSession().invalidate();
        HttpSession session = httpServletRequest.getSession(true);

        // 로그인
        User user = authService.login(new LoginRequest(email, password));
        session.setAttribute("user", user);
        session.setMaxInactiveInterval(60 * 30); // 30분
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
     * @param email              이메일
     * @param password           비밀번호
     * @param name               이름
     * @param httpServletRequest HttpServletRequest(세션)
     * @return 홈 화면
     */
    @PostMapping("/signup")
    public String signupPost(String email, String password, String name, HttpServletRequest httpServletRequest) {
        User user = authService.signUp(new SignUpRequest(email, password, name));

        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("user", user);
        session.setMaxInactiveInterval(60 * 30); // 30분
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest httpServletRequest) {
        httpServletRequest.getSession().invalidate();
        return "redirect:/";
    }
}
