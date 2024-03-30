package ac.kr.deu.connect.luck.user;

import ac.kr.deu.connect.luck.exception.CustomErrorCode;
import ac.kr.deu.connect.luck.exception.CustomErrorResponse;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("[GET] 사용자 정보 조회 - 성공")
    void getUserInfoSuccess() throws Exception {
        // Given
        User user = User.builder()
                .id(1L)
                .email("test1@test.com")
                .build();
        // When
        MvcResult result = mockMvc.perform(get("/api/user/1"))
                .andExpect(status().isOk())
                .andReturn();
        User responseUser = objectMapper.readValue(result.getResponse().getContentAsString(), User.class);

        // Then
        Assertions.assertEquals(user.getId(), responseUser.getId());
        Assertions.assertEquals(user.getEmail(), responseUser.getEmail());
    }

    @Test
    @DisplayName("[GET] 사용자 정보 조회 - 실패(존재하지 않는 사용자)")
    void getUserInfoFail() throws Exception {
        mockMvc.perform(get("/api/user/100"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @DisplayName("[POST]User Role 설정 - 성공")
    void setUserRoleSuccess() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/user/set-role")
                        .param("id", "1")
                        .param("role", UserRole.ADMIN.name()))
                .andExpect(status().isOk())
                .andReturn();

        User user = objectMapper.readValue(result.getResponse().getContentAsString(), User.class);
        Assertions.assertEquals(UserRole.ADMIN, user.getRole());
    }

    @Test
    @Transactional
    @DisplayName("[POST]User Role 설정 - 실패(존재하지 않는 사용자)")
    void setUserRoleFail() throws Exception {
        mockMvc.perform(post("/api/user/set-role")
                        .param("id", "100")
                        .param("role", UserRole.ADMIN.name()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @DisplayName("[POST]User Role 설정 - 실패(권한이 같음)")
    void setUserRoleFailSameRole() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/user/set-role")
                        .param("id", "1")
                        .param("role", UserRole.USER.name()))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        CustomErrorResponse response = objectMapper.readValue(content, CustomErrorResponse.class);

        Assertions.assertEquals(CustomErrorCode.ALREADY_SET_ROLE, response.errorType());
    }

    @Test
    @Transactional
    @DisplayName("[POST]User Role 설정 - 실패(푸드트럭 매니저는 이벤트 매니저로 변경 불가)")
    void setUserRoleFailFoodTruckManager() throws Exception {
        mockMvc.perform(post("/api/user/set-role")
                        .param("id", "6")
                        .param("role", UserRole.EVENT_MANAGER.name()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @DisplayName("[POST]User Role 설정 - 실패(이벤트 매니저는 푸드트럭 매니저로 변경 불가)")
    void setUserRoleFailEventManager() throws Exception {
        mockMvc.perform(post("/api/user/set-role")
                        .param("id", "4")
                        .param("role", UserRole.FOOD_TRUCK_MANAGER.name()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @DisplayName("[POST]User Role 설정 - 실패(존재하지 않는 사용자)")
    void setUserRoleFailUnknownUser() throws Exception {
        // When
        MvcResult result = mockMvc.perform(post("/api/user/set-role")
                        .param("id", "100")
                        .param("role", UserRole.ADMIN.name()))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        CustomErrorResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), CustomErrorResponse.class);
        Assertions.assertEquals(CustomErrorCode.USER_ID_NOT_MATCH, response.errorType());
    }

}
