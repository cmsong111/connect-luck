package ac.kr.deu.connect.luck.auth;

import ac.kr.deu.connect.luck.exception.CustomErrorCode;
import ac.kr.deu.connect.luck.exception.CustomErrorResponse;
import ac.kr.deu.connect.luck.user.User;
import ac.kr.deu.connect.luck.user.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("로그인 - 성공")
    void loginSuccess() throws Exception {

        LoginRequest loginRequest = new LoginRequest("test1@test.com", "test1");
        String content = objectMapper.writeValueAsString(loginRequest);

        mockMvc.perform(post("/api/login")
                        .contentType("application/json")
                        .content(content))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인 - ID 없음")
    void loginFailureId() throws Exception {

        LoginRequest loginRequest = new LoginRequest("test123@test.com", "test1");
        String content = objectMapper.writeValueAsString(loginRequest);
        MvcResult mvcResult = mockMvc.perform(post("/api/login")
                        .contentType("application/json")
                        .characterEncoding("UTF-8")
                        .content(content))
                .andExpect(status().isBadRequest())
                .andReturn();

        CustomErrorResponse customErrorResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CustomErrorResponse.class);
        CustomErrorCode customErrorCode = CustomErrorCode.EMAIL_NOT_FOUND;

        Assertions.assertEquals(customErrorResponse.errorType(), customErrorCode);
    }

    @Test
    @DisplayName("로그인 - 비밀번호 불일치")
    void loginFailurePassword() throws Exception {

        LoginRequest loginRequest = new LoginRequest("test1@test.com", "test123");
        String content = objectMapper.writeValueAsString(loginRequest);
        MvcResult mvcResult = mockMvc.perform(post("/api/login")
                        .contentType("application/json")
                        .characterEncoding("UTF-8")
                        .content(content))
                .andExpect(status().isBadRequest())
                .andReturn();

        CustomErrorResponse customErrorResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CustomErrorResponse.class);
        CustomErrorCode customErrorCode = CustomErrorCode.PASSWORD_NOT_MATCH;

        Assertions.assertEquals(customErrorResponse.errorType(), customErrorCode);
    }

    @Test
    @DisplayName("회원가입 - 성공")
    @Transactional
    void signUp() throws Exception {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("tttt@tttt.com", "tttt1234", "tttt", "010-1234-5678");
        String content = objectMapper.writeValueAsString(signUpRequest);

        // when
        MvcResult mvcResult = mockMvc.perform(post("/api/signup")
                        .contentType("application/json")
                        .content(content))
                .andExpect(status().isOk())
                .andReturn();

        // then
        User user = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);
        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals(user.getEmail(), signUpRequest.email());
        Assertions.assertEquals(user.getName(), signUpRequest.name());

    }

    @Test
    @DisplayName("회원가입 - 중복된 이메일")
    void signUpFailure() throws Exception {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("test1@test.com", "test1", "test1", "010-1234-5679");
        String content = objectMapper.writeValueAsString(signUpRequest);

        // when
        MvcResult mvcResult = mockMvc.perform(post("/api/signup")
                        .contentType("application/json")
                        .content(content))
                .andExpect(status().isBadRequest())
                .andReturn();

        // then
        CustomErrorResponse customErrorResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CustomErrorResponse.class);
        CustomErrorCode customErrorCode = CustomErrorCode.ALREADY_EXIST_USER_ID;

        Assertions.assertEquals(customErrorResponse.errorType(), customErrorCode);

    }

    @Test
    @DisplayName("휴대폰 번호로 이메일 찾기 - 성공")
    void findEmailByPhoneSuccess() throws Exception {
        // given
        String phone = "010-1111-1111";
        String email = "test1@test.com";

        // when
        MvcResult mvcResult = mockMvc.perform(post("/api/findEmailByPhone")
                        .param("phone", phone))
                .andExpect(status().isOk())
                .andReturn();

        // then
        String responseEmail = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals(email, responseEmail);
    }

    @Test
    @DisplayName("휴대폰 번호로 이메일 찾기 - 실패")
    void findEmailByPhoneFailure() throws Exception {
        // given
        String phone = "010-1234-5678";

        // when
        MvcResult mvcResult = mockMvc.perform(post("/api/findEmailByPhone")
                        .param("phone", phone))
                .andExpect(status().isBadRequest())
                .andReturn();

        // then
        CustomErrorResponse customErrorResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CustomErrorResponse.class);
        CustomErrorCode customErrorCode = CustomErrorCode.PHONE_NOT_FOUND;

        Assertions.assertEquals(customErrorResponse.errorType(), customErrorCode);
    }

    @Test
    @DisplayName("유저 정보 조회 - 성공")
    void getUserInfoSuccess() throws Exception {
        // given
        User user = User.builder()
                .id(1L)
                .email("test1@test.com")
                .password("test1")
                .name("User1")
                .phone("010-1111-1111")
                .role(UserRole.USER)
                .build();

        // when
        MvcResult mvcResult = mockMvc.perform(get("/api/user/{id}", user.getId())
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        // then
        User responseUser = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);

        Assertions.assertEquals(user.getId(), responseUser.getId());
        Assertions.assertEquals(user.getEmail(), responseUser.getEmail());
        Assertions.assertEquals(user.getName(), responseUser.getName());
        Assertions.assertEquals(user.getPhone(), responseUser.getPhone());
        Assertions.assertEquals(user.getRole(), responseUser.getRole());
    }

    @Test
    @DisplayName("유저 정보 조회 - 실패")
    void getUserInfoFailure() throws Exception {
        // given
        Long id = 100L;

        // when
        MvcResult mvcResult = mockMvc.perform(get("/api/user/{id}", id)
                        .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andReturn();

        // then
        CustomErrorResponse customErrorResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CustomErrorResponse.class);
        CustomErrorCode customErrorCode = CustomErrorCode.ID_NOT_MATCH;

        Assertions.assertEquals(customErrorResponse.errorType(), customErrorCode);
    }

    @Test
    @DisplayName("유저 정보 수정 - 성공")
    @Transactional
    void updateUserSuccess() throws Exception {
        User firstUser = User.builder()
                .email("test99@test.com")
                .password("test99")
                .name("User99")
                .phone("010-0099-0099")
                .role(UserRole.USER)
                .build();

        User savedUser = objectMapper.readValue(mockMvc.perform(post("/api/signup")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(firstUser)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(), User.class);

        Assertions.assertNotNull(savedUser.getId());

        String updatedName = "User99-1";
        String updatedPhone = "010-0099-0098";
        String updatedPassword = "test99-1";
        String updatedEmail = "test99-1@test.com";

        SignUpRequest signUpRequest = new SignUpRequest(updatedEmail, updatedPassword, updatedName, updatedPhone);

        MvcResult mvcResult = mockMvc.perform(patch("/api/user/{id}", savedUser.getId())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk())
                .andReturn();

        User updatedUser = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);

        Assertions.assertEquals(savedUser.getId(), updatedUser.getId());
        Assertions.assertEquals(updatedEmail, updatedUser.getEmail());
        Assertions.assertEquals(updatedName, updatedUser.getName());
        Assertions.assertEquals(updatedPassword, updatedUser.getPassword());
        Assertions.assertEquals(updatedPhone, updatedUser.getPhone());
    }

    @Test
    @DisplayName("유저 정보 수정 - 이름,전화번호만 수정")
    @Transactional
    void updateUserNameAndPhoneSuccess() throws Exception {
        User firstUser = User.builder()
                .email("test99@test.com")
                .password("test99")
                .name("User99")
                .phone("010-0099-0099")
                .role(UserRole.USER)
                .build();

        User savedUser = objectMapper.readValue(mockMvc.perform(post("/api/signup")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(firstUser)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(), User.class);

        Assertions.assertNotNull(savedUser.getId());

        String updatedName = "User99-1";
        String updatedPhone = "010-0099-0098";

        SignUpRequest signUpRequest = new SignUpRequest(null, null, updatedName, updatedPhone);

        MvcResult mvcResult = mockMvc.perform(patch("/api/user/{id}", savedUser.getId())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk())
                .andReturn();

        User updatedUser = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);

        Assertions.assertEquals(savedUser.getId(), updatedUser.getId());
        Assertions.assertEquals(savedUser.getEmail(), updatedUser.getEmail());
        Assertions.assertEquals(updatedName, updatedUser.getName());
        Assertions.assertEquals(savedUser.getPassword(), updatedUser.getPassword());
        Assertions.assertEquals(updatedPhone, updatedUser.getPhone());
    }

}
