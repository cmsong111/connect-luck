package ac.kr.deu.connect.luck.auth;

import ac.kr.deu.connect.luck.configuration.MapStructMapper;
import ac.kr.deu.connect.luck.exception.CustomErrorCode;
import ac.kr.deu.connect.luck.exception.CustomException;
import ac.kr.deu.connect.luck.user.User;
import ac.kr.deu.connect.luck.user.UserRepository;
import ac.kr.deu.connect.luck.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


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
        if (userRepository.existsByEmail(signUpRequest.email())) throw new CustomException(CustomErrorCode.ALREADY_EXIST_USER_ID);

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
        Optional<User> user = userRepository.findByEmail(loginRequest.email());

        if (user.isEmpty()) throw new CustomException(CustomErrorCode.EMAIL_NOT_FOUND);
        if (!user.get().getPassword().equals(loginRequest.password())) throw new CustomException(CustomErrorCode.PASSWORD_NOT_MATCH);

        return user.get();
    }

    public String findEmailByPhone(String phone) {
        Optional<User> user = userRepository.findByPhone(phone);
        if (user.isEmpty()) throw new CustomException(CustomErrorCode.PHONE_NOT_FOUND);
        return user.get().getEmail();
    }
}
