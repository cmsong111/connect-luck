package ac.kr.deu.connect.luck.auth;

import ac.kr.deu.connect.luck.exception.CustomErrorCode;
import ac.kr.deu.connect.luck.exception.CustomException;
import ac.kr.deu.connect.luck.security.JwtTokenProvider;
import ac.kr.deu.connect.luck.user.User;
import ac.kr.deu.connect.luck.user.UserMapper;
import ac.kr.deu.connect.luck.user.UserRepository;
import ac.kr.deu.connect.luck.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 회원가입
     *
     * @param signUpRequest 회원가입 요청 정보
     * @return String jwt 토큰
     */
    public String signUp(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.email())) throw new CustomException(CustomErrorCode.ALREADY_EXIST_USER_ID);

        User user = userMapper.toUser(signUpRequest);
        List<UserRole> roles = new ArrayList<>();
        roles.add(UserRole.USER);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        System.out.println(savedUser.getPassword());
        return jwtTokenProvider.createToken(savedUser.getEmail(), savedUser.getRoles());
    }

    /**
     * 로그인
     *
     * @param loginRequest 로그인 요청 정보
     * @return User 엔티티
     */
    public String login(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByEmail(loginRequest.email());

        if (user.isEmpty()) throw new CustomException(CustomErrorCode.EMAIL_NOT_FOUND);
        if (!passwordEncoder.matches(loginRequest.password(), user.get().getPassword())) throw new CustomException(CustomErrorCode.PASSWORD_NOT_MATCH);

        return jwtTokenProvider.createToken(user.get().getEmail(), user.get().getRoles());

    }

    public String findEmailByPhone(String phone) {
        Optional<User> user = userRepository.findByPhone(phone);
        if (user.isEmpty()) throw new CustomException(CustomErrorCode.PHONE_NOT_FOUND);
        return user.get().getEmail();
    }
}
