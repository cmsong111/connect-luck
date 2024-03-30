package ac.kr.deu.connect.luck.food_truck;

import ac.kr.deu.connect.luck.exception.CustomErrorCode;
import ac.kr.deu.connect.luck.exception.CustomErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FoodTruckRestControllerTest {


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("[POST] 푸드트럭 신규 생성 - 성공")
    @Transactional
    void createFoodTruckSuccess() throws Exception {
        // Given
        FoodTruckRequest foodTruckRequest = new FoodTruckRequest("푸드트럭1", "서울시 강남구에서 제일 잘나가는 식당", "https://picsum.photos/1600/900", FoodType.DESSERT);
        Long userId = 8L;

        // When
        MvcResult mvcResult = mockMvc.perform(post("/api/food-truck")
                        .param("userId", String.valueOf(userId))
                        .content(objectMapper.writeValueAsString(foodTruckRequest))
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        FoodTruck foodTruck = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), FoodTruck.class);
        assertEquals(foodTruckRequest.name(), foodTruck.getName());
        assertEquals(foodTruckRequest.description(), foodTruck.getDescription());
        assertEquals(foodTruckRequest.imageUrl(), foodTruck.getImageUrl());
        assertEquals(foodTruckRequest.foodType(), foodTruck.getFoodType());
        assertEquals(userId, foodTruck.getManager().getId());
    }

    @Test
    @DisplayName("[POST] 푸드트럭 신규 생성 - 실패(유저가 존재하지 않음)")
    @Transactional
    void createFoodTruckFail() throws Exception {
        // Given
        FoodTruckRequest foodTruckRequest = new FoodTruckRequest("푸드트럭1", "서울시 강남구에서 제일 잘나가는 식당", "https://picsum.photos/1600/900", FoodType.DESSERT);
        Long userId = 100L;

        // When
        MvcResult mvcResult =  mockMvc.perform(post("/api/food-truck")
                        .param("userId", String.valueOf(userId))
                        .content(objectMapper.writeValueAsString(foodTruckRequest))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        CustomErrorResponse customErrorResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CustomErrorResponse.class);
        assertEquals(CustomErrorCode.USER_ID_NOT_MATCH, customErrorResponse.errorType());
    }

    @Test
    @DisplayName("[POST] 푸드트럭 신규 생성 - 실패(유저 권한 부족)")
    @Transactional
    void createFoodTruckFailNotManager() throws Exception {
        // Given
        FoodTruckRequest foodTruckRequest = new FoodTruckRequest("푸드트럭1", "서울시 강남구에서 제일 잘나가는 식당", "https://picsum.photos/1600/900", FoodType.DESSERT);
        Long userId = 1L;

        // When
        MvcResult mvcResult = mockMvc.perform(post("/api/food-truck")
                        .param("userId", String.valueOf(userId))
                        .content(objectMapper.writeValueAsString(foodTruckRequest))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        CustomErrorResponse customErrorResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CustomErrorResponse.class);
        assertEquals(CustomErrorCode.ROLE_NOT_MATCH, customErrorResponse.errorType());
    }


}
