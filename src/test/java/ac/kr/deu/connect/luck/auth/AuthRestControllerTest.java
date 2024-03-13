package ac.kr.deu.connect.luck.auth;

import ac.kr.deu.connect.luck.exception.CustomErrorCode;
import ac.kr.deu.connect.luck.exception.CustomErrorResponse;
import ac.kr.deu.connect.luck.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        CustomErrorCode customErrorCode = CustomErrorCode.ID_NOT_FOUND;

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
    void signUp() throws Exception {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("tttt@tttt.com", "tttt1234", "tttt");
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
        SignUpRequest signUpRequest = new SignUpRequest("test1@test.com", "test1", "test1");
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
}
