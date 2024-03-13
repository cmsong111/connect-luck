package ac.kr.deu.connect.luck.auth;

import ac.kr.deu.connect.luck.user.User;
import ac.kr.deu.connect.luck.user.UserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    AuthService authService;

    @Test
    @DisplayName("회원가입 - 성공")
    void signUp() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("test@test.com", "test1234", "test");
        // when
        User user = authService.signUp(signUpRequest);
        // then
        assertNotNull(user.getId());
        assertEquals(user.getEmail(), signUpRequest.email());
        assertEquals(user.getName(), signUpRequest.name());
        assertEquals(user.getRole(), UserRole.USER);
    }

    @Test
    @DisplayName("로그인 - 성공")
    void loginSuccess() {
        // given
        LoginRequest loginRequest = new LoginRequest("test1@test.com", "test1");
        // when
        User user = authService.login(loginRequest);
        // then
        assertNotNull(user.getId());
    }

    @Test
    void loginFail() {
        // given
        LoginRequest loginRequest = new LoginRequest("test100@test.com", "test100");
        // when
        // then
        Assertions.assertThrows(IllegalArgumentException.class, () -> authService.login(loginRequest));
    }

    @Test
    void testLogin() {
    }
}
