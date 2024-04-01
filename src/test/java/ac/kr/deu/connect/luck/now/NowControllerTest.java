package ac.kr.deu.connect.luck.now;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NowControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("[GET]현재 운영중인 푸드트럭 조회(위도, 경도 없이)")
    void getNow() throws Exception {

        // When
        MvcResult result = mockMvc.perform(get("/api/now"))
                .andExpect(status().isOk())
                .andReturn();

        //  Then
        List<Now> nowList = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Now>>() {
        });

        for (Now now : nowList) {
            Assertions.assertTrue(now.getIsOperating());
        }
    }

    @Test
    @DisplayName("[POST] 푸드트럭 운영 시작 - 성공")
    void startWorkingSuccess() throws Exception {
        Long foodTruckId = 1L;
        Long userId = 6L;
        Double latitude = 37.123456;
        Double longitude = 127.123456;

        // When
        MvcResult result = mockMvc.perform(post("/api/now/start/{foodTruckId}", foodTruckId)
                        .header("USER_ID", Long.toString(userId))
                        .param("latitude", Double.toString(latitude))
                        .param("longitude", Double.toString(longitude))
                )
                .andExpect(status().isOk())
                .andReturn();

        // Then
        Now now = objectMapper.readValue(result.getResponse().getContentAsString(), Now.class);

        Assertions.assertEquals(foodTruckId, now.getFoodTruck().getId());
        Assertions.assertEquals(latitude, now.getLatitude());
        Assertions.assertEquals(longitude, now.getLongitude());
        Assertions.assertTrue(now.getIsOperating());
    }

    @Test
    @DisplayName("[POST] 푸드트럭 운영 시작 - 실패(위도 누락)")
    void startWorkingFailureBecauseOfMissingLatitude() throws Exception {
        Long foodTruckId = 1L;
        long userId = 6L;
        double longitude = 127.123456;

        // When
        mockMvc.perform(post("/api/now/start/{foodTruckId}", foodTruckId)
                        .header("USER_ID", Long.toString(userId))
                        .param("longitude", Double.toString(longitude))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("[POST] 푸드트럭 운영 시작 - 실패(매니저 권한 없음)")
    void startWorkingFailureBecauseOfUnauthorizedUser() throws Exception {
        Long foodTruckId = 1L;
        long userId = 1L;
        double latitude = 37.123456;
        double longitude = 127.123456;

        // When
        mockMvc.perform(post("/api/now/start/{foodTruckId}", foodTruckId)
                        .header("USER_ID", Long.toString(userId))
                        .param("latitude", Double.toString(latitude))
                        .param("longitude", Double.toString(longitude))
                )
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("[POST] 푸드트럭 운영 종료 - 성공")
    void stopWorkingSuccess() throws Exception {
        Long foodTruckId = 1L;
        Long userId = 6L;

        // When
        MvcResult result = mockMvc.perform(post("/api/now/end/{foodTruckId}", foodTruckId)
                        .header("USER_ID", Long.toString(userId))
                )
                .andExpect(status().isOk())
                .andReturn();

        // Then
        Now now = objectMapper.readValue(result.getResponse().getContentAsString(), Now.class);

        Assertions.assertEquals(foodTruckId, now.getFoodTruck().getId());
        Assertions.assertFalse(now.getIsOperating());
    }
}
