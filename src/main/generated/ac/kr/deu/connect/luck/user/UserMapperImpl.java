package ac.kr.deu.connect.luck.user;

import ac.kr.deu.connect.luck.auth.dto.LoginRequest;
import ac.kr.deu.connect.luck.auth.dto.SignUpRequest;

import javax.annotation.processing.Generated;

import org.springframework.stereotype.Component;

@Generated(
        value = "org.mapstruct.ap.MappingProcessor",
        date = "2024-05-14T13:34:13+0900",
        comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(SignUpRequest signUpRequest) {
        if (signUpRequest == null) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email(signUpRequest.email());
        user.password(signUpRequest.password());
        user.name(signUpRequest.name());

        return user.build();
    }

    @Override
    public User toUser(LoginRequest loginRequest) {
        if (loginRequest == null) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email(loginRequest.getEmail());
        user.password(loginRequest.getPassword());

        return user.build();
    }
}
