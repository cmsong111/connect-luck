package ac.kr.deu.connect.luck.user;

import ac.kr.deu.connect.luck.auth.dto.LoginRequest;
import ac.kr.deu.connect.luck.auth.dto.SignUpRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(SignUpRequest signUpRequest);

    User toUser(LoginRequest loginRequest);
}
