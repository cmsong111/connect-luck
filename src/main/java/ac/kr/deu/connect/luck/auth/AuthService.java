package ac.kr.deu.connect.luck.auth;

import ac.kr.deu.connect.luck.configuration.MapStructMapper;
import ac.kr.deu.connect.luck.user.User;
import ac.kr.deu.connect.luck.user.UserRepository;
import ac.kr.deu.connect.luck.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final MapStructMapper mapStructMapper;

    /**
     * 회원가입
     *
     * @param signUpRequest 회원가입 요청 정보
     * @return User 엔티티
     */
    public User signUp(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.email())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }
        User user = mapStructMapper.toUser(signUpRequest);
        user.setRole(UserRole.USER);
        return userRepository.save(user);
    }

    /**
     * 로그인
     *
     * @param loginRequest 로그인 요청 정보
     * @return User 엔티티
     */
    public User login(LoginRequest loginRequest) {
        return userRepository.findByEmailAndPassword(loginRequest.email(), loginRequest.password())
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다."));
    }

    public User login(String email, String password) {
        LoginRequest loginRequest = new LoginRequest(email, password);
        return login(loginRequest);
    }
}
