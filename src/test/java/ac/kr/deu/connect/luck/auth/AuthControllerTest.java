package ac.kr.deu.connect.luck.auth;

import ac.kr.deu.connect.luck.exception.CustomErrorCode;
import ac.kr.deu.connect.luck.exception.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(AuthController.class)
@MockBean(JpaMetamodelMappingContext.class)
class AuthControllerTest {

    @MockBean
    private AuthService authService;

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("[GET] 로그인 페이지")
    @Test
    void login() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/login"));
    }

    @DisplayName("[POST] 로그인 페이지 - 성공")
    @Test
    void loginPost() throws Exception {
        String email = "test1@test.com";
        String password = "test1";

        mockMvc.perform(post("/login")
                        .param("email", email)
                        .param("password", password))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @DisplayName("[GET] 약관 동의 페이지")
    @Test
    void agreement() throws Exception {
        mockMvc.perform(get("/agreement"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/agreement"));
    }

    @Test
    @DisplayName("[GET] 회원가입 페이지")
    void signup() throws Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/signup"));
    }

    @Test
    @DisplayName("[POST] 회원가입 페이지 - 성공")
    void signupPost() throws Exception {
        String email = "email@test.com";
        String password = "password";
        String name = "name";
        String phone = "010-1234-9875";

        mockMvc.perform(post("/signup")
                        .param("email", email)
                        .param("password", password)
                        .param("name", name)
                        .param("phone", phone))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

    }


    @Test
    @DisplayName("[GET] 아이디 찾기 페이지")
    void requestIdSearchPage() throws Exception {
        mockMvc.perform(get("/find-id"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/find-id"));

    }

    @Test
    @DisplayName("[POST] 아이디 찾기 페이지 - 성공")
    void idSearchPost() throws Exception {
        String phone = "010-1111-1111";
        mockMvc.perform(post("/find-id")
                        .param("phone", phone))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/found-id"));
    }

    @Test
    @DisplayName("[POST] 아이디 찾기 - 실패")
    void idSearchPostFail() throws Exception {
        // given
        String phone = "010-5050-5050";

        // when
        when(authService.findEmailByPhone(phone)).thenThrow(new CustomException(CustomErrorCode.PHONE_NOT_FOUND));

        // then
        mockMvc.perform(post("/find-id")
                        .param("phone", phone))
                .andExpect(status().isBadRequest());
    }
}
