package ac.kr.deu.connect.luck.auth.service;

import ac.kr.deu.connect.luck.auth.JwtTokenProvider;
import ac.kr.deu.connect.luck.auth.controller.request.LoginRequest;
import ac.kr.deu.connect.luck.auth.controller.request.SignUpRequest;
import ac.kr.deu.connect.luck.auth.controller.response.EmailResponse;
import ac.kr.deu.connect.luck.auth.controller.response.IdCheckResponse;
import ac.kr.deu.connect.luck.auth.controller.response.TokenResponse;
import ac.kr.deu.connect.luck.exception.CustomErrorCode;
import ac.kr.deu.connect.luck.exception.CustomException;
import ac.kr.deu.connect.luck.user.entity.User;
import ac.kr.deu.connect.luck.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 회원가입
     *
     * @param signUpRequest 회원가입 요청 정보
     * @return String jwt 토큰
     */
    public TokenResponse signUp(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.email()))
            throw new CustomException(CustomErrorCode.ALREADY_EXIST_USER_ID);


        User savedUser = userRepository.save(User.Companion.create(
                signUpRequest.email(),
                passwordEncoder.encode(signUpRequest.password()),
                signUpRequest.name(),
                signUpRequest.phoneNumber()
        ));

        return new TokenResponse(
                jwtTokenProvider.createToken(savedUser.getId(), savedUser.getEmail(), savedUser.getRoles(), null)
        );
    }

    /**
     * 로그인
     *
     * @param loginRequest 로그인 요청 정보
     * @return User 엔티티
     */
    public TokenResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());

//        if (user.isEmpty()) throw new CustomException(CustomErrorCode.EMAIL_NOT_FOUND);

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
            throw new CustomException(CustomErrorCode.PASSWORD_NOT_MATCH);

        return new TokenResponse(
                jwtTokenProvider.createToken(user.getId(), user.getEmail(), user.getRoles(), null)
        );

    }

    public EmailResponse findEmailByPhone(String phone) {
        User user = userRepository.findByPhone(phone);
//        if (user.isEmpty()) throw new CustomException(CustomErrorCode.PHONE_NOT_FOUND);
        return new EmailResponse(user.getEmail());
    }

    /**
     * 아이디 중복 체크
     *
     * @param email 사용하려는 이메일
     * @return True: 사용 가능, False: 사용중인 이메일
     */
    public IdCheckResponse idCheck(String email) {
        return new IdCheckResponse(!userRepository.existsByEmail(email));
    }
}
