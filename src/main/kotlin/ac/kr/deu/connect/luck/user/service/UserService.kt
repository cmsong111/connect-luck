package ac.kr.deu.connect.luck.user.service

import ac.kr.deu.connect.luck.common.exception.BadRequestException
import ac.kr.deu.connect.luck.common.exception.NotFoundException
import ac.kr.deu.connect.luck.common.storage.StorageService
import ac.kr.deu.connect.luck.user.controller.request.UserUpdateForm
import ac.kr.deu.connect.luck.user.controller.response.UserDetailResponse
import ac.kr.deu.connect.luck.user.entity.User
import ac.kr.deu.connect.luck.user.entity.UserRole
import ac.kr.deu.connect.luck.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val storageService: StorageService,
) {
    /**
     * 비밀번호 변경
     */
    @Transactional
    fun changePassword(
        email: String,
        currentPassword: String,
        newPassword: String
    ) {
        val user = userRepository.findByEmail(email)
            ?: throw NotFoundException(User::class.java, mapOf("email" to email))

        if (!passwordEncoder.matches(currentPassword, user.password)) {
            throw BadRequestException("현재 비밀번호가 잘못되었습니다.")
        }
        user.updatePassword(
            password = newPassword,
            passwordEncoder = passwordEncoder,
        )

        userRepository.save(user)
    }

    /**
     * 이메일로 사용자 조회
     */
    @Transactional(readOnly = true)
    fun getUserByEmail(email: String): UserDetailResponse {
        return UserDetailResponse.from(
            user = userRepository.findByEmail(email)
                ?: throw NotFoundException(User::class.java, mapOf("email" to email))
        )
    }

    /**
     * 사용자 식별자로 사용자 조회
     */
    @Transactional(readOnly = true)
    fun getUserById(id: Long): User {
        return userRepository.findByIdOrNull(id)
            ?: throw NotFoundException(User::class.java, mapOf("id" to id))
    }

    /**
     * 탈퇴
     */
    @Transactional
    fun withdraw(email: String) {
        val user = userRepository.findByEmail(email)
            ?: throw NotFoundException(User::class.java, mapOf("email" to email))

        user.updateEmailToDeletedEmail()

        userRepository.delete(user)
    }

    /**
     * 유저 역할 추가
     */
    @Transactional
    fun addRole(email: String, role: UserRole): UserDetailResponse {
        val user = userRepository.findByEmail(email)
            ?: throw NotFoundException(User::class.java, mapOf("email" to email))

        user.roles.add(role)

        return UserDetailResponse.from(user = user)
    }

    /**
     * 유저 정보 업데이트
     */
    @Transactional
    fun updateUserInfo(
        email: String,
        userUpdateForm: UserUpdateForm,
    ): UserDetailResponse {
        val user: User = userRepository.findByEmail(email)
            ?: throw NotFoundException(User::class.java, mapOf("email" to email))

        user.update(
            name = userUpdateForm.name,
            phone = userUpdateForm.phone,
            roles = userUpdateForm.roles.toSet(),
        )

        return UserDetailResponse.from(user = user)
    }

    /**
     * 프로필 이미지 업데이트
     */
    @Transactional
    fun updateProfileImage(
        email: String,
        image: MultipartFile,
    ): UserDetailResponse {
        val user = userRepository.findByEmail(email)
            ?: throw NotFoundException(User::class.java, mapOf("email" to email))

        user.update(
            profileImage = storageService.save(image)
        )

        return UserDetailResponse.from(user = user)
    }
}
