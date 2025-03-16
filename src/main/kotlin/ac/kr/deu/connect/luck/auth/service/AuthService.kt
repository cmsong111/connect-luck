package ac.kr.deu.connect.luck.auth.service

import ac.kr.deu.connect.luck.auth.JwtTokenProvider
import ac.kr.deu.connect.luck.auth.controller.request.SignupRequest
import ac.kr.deu.connect.luck.auth.exception.EmailAlreadyExistsException
import ac.kr.deu.connect.luck.auth.exception.EmailOrPasswordIncorrectException
import ac.kr.deu.connect.luck.auth.exception.UserNotFoundException
import ac.kr.deu.connect.luck.user.entity.User
import ac.kr.deu.connect.luck.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider,
) {

    /**
     * 회원가입
     * @param signupRequest 회원가입 요청 폼
     * @return Jwt 토큰
     */
    @Transactional
    fun signup(signupRequest: SignupRequest): String {
        if (userRepository.existsByEmail(signupRequest.email)) {
            throw EmailAlreadyExistsException()
        }

        val user = userRepository.save(
            User.create(
                email = signupRequest.email,
                password = passwordEncoder.encode(signupRequest.password),
                name = signupRequest.name,
                phone = signupRequest.phoneNumber,
            )
        )

        return jwtTokenProvider.createToken(
            userId = user.id,
            email = user.email,
            roles = user.roles,
        )
    }

    /**
     * 로그인
     * @param email 이메일
     * @param password 비밀번호
     * @return Jwt 토큰
     */
    @Transactional(readOnly = true)
    fun login(email: String, password: String): String {
        val user = userRepository.findByEmail(email)
            ?: throw EmailOrPasswordIncorrectException()

        if (!passwordEncoder.matches(password, user.password)) {
            throw EmailOrPasswordIncorrectException()
        }

        return jwtTokenProvider.createToken(
            userId = user.id,
            email = user.email,
            roles = user.roles,
        )
    }

    /**
     * 이메일 사용가능 여부 확인
     * @param email 이메일
     * @return 사용가능 여부 (true: 사용가능, false: 중복)
     */
    @Transactional(readOnly = true)
    fun checkEmailDuplication(email: String): Boolean {
        return !userRepository.existsByEmail(email)
    }

    /**
     * 전화번호로 회원 조회
     * @param phone 전화번호
     * @return 사용자 이메일
     */
    @Transactional(readOnly = true)
    fun findEmailByPhone(phone: String): String {
        val user = userRepository.findByPhone(phone)
            ?: throw UserNotFoundException("User not found")
        return user.email
    }
}
