package ac.kr.deu.connect.luck.user.entity

import org.springframework.security.core.GrantedAuthority

enum class UserRole : GrantedAuthority {
    USER,
    ADMIN,
    EVENT_MANAGER,
    FOOD_TRUCK_MANAGER,
    ;

    override fun getAuthority(): String {
        return "ROLE_$name"
    }
}
