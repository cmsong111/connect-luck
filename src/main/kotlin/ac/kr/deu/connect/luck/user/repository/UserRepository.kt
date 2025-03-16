package ac.kr.deu.connect.luck.user.repository

import ac.kr.deu.connect.luck.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    /** 이메일로 사용자 조회 */
    fun findByEmail(email: String): User?

    /** 이메일 중복 확인 */
    fun existsByEmail(email: String): Boolean

    /** 전화번호로 사용자 조회 */
    fun findByPhone(phone: String): User?
}
