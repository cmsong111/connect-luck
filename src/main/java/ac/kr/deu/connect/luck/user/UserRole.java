package ac.kr.deu.connect.luck.user;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    EVENT_MANAGER, USER, ADMIN, FOOD_TRUCK_MANAGER;

    @Override
    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
