package ac.kr.deu.connect.luck.auth.service

import ac.kr.deu.connect.luck.user.entity.User
import ac.kr.deu.connect.luck.user.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository,
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user: User = userRepository.findByEmail(username)
            ?: throw UsernameNotFoundException("User not found with email: $username")

        return org.springframework.security.core.userdetails.User.builder()
            .username(user.email)
            .password(user.password)
            .authorities(user.roles)
            .accountExpired(false)
            .accountLocked(false)
            .credentialsExpired(false)
            .disabled(false)
            .build()
    }
}
