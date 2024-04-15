package ac.kr.deu.connect.luck.food_truck;

import ac.kr.deu.connect.luck.exception.CustomErrorCode;
import ac.kr.deu.connect.luck.exception.CustomErrorResponse;
import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckMenuRequest;
import ac.kr.deu.connect.luck.food_truck.entity.FoodTruckMenu;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FoodTruckMenuRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("[POST] 푸드트럭 메뉴 등록 - 성공")
    @Transactional
    void saveFoodTruckMenuSuccess() throws Exception {
        // Given
        long userId = 6L;
        long foodTruckId = 1L;
        FoodTruckMenuRequest foodTruckMenuRequest = new FoodTruckMenuRequest("메뉴 이름", "메뉴 설명", "imageURl", 10000);

        // When
        MvcResult result = mockMvc.perform(post("/api/food-truck/{foodTruckId}/menu", foodTruckId)
                        .header("USER_ID", Long.toString(userId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(foodTruckMenuRequest)))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        FoodTruckMenu foodTruckMenu = objectMapper.readValue(result.getResponse().getContentAsString(), FoodTruckMenu.class);

        assertNotNull(foodTruckMenu.getId());
        assertEquals(foodTruckMenuRequest.name(), foodTruckMenu.getName());
        assertEquals(foodTruckMenuRequest.description(), foodTruckMenu.getDescription());
        assertEquals(foodTruckMenuRequest.imageUrl(), foodTruckMenu.getImageUrl());
        assertEquals(foodTruckMenuRequest.price(), foodTruckMenu.getPrice());

    }

    @Test
    @DisplayName("[POST] 푸드트럭 메뉴 등록 - 실패(유저 ID 누락)")
    @Transactional
    void saveFoodTruckMenuFailureBecauseOfMissingUserId() throws Exception {
        // Given
        long foodTruckId = 1L;
        FoodTruckMenuRequest foodTruckMenuRequest = new FoodTruckMenuRequest("메뉴 이름", "메뉴 설명", "imageURl", 10000);

        // When
        mockMvc.perform(post("/api/food-truck/{foodTruckId}/menu", foodTruckId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(foodTruckMenuRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("[POST] 푸드트럭 메뉴 등록 - 실패(유저 권한 없음)")
    @Transactional
    void saveFoodTruckMenuFailureBecauseOfUnauthorizedUser() throws Exception {
        // Given
        long userId = 1L;
        long foodTruckId = 1L;
        FoodTruckMenuRequest foodTruckMenuRequest = new FoodTruckMenuRequest("메뉴 이름", "메뉴 설명", "imageURl", 10000);

        // When
        MvcResult mvcResult = mockMvc.perform(post("/api/food-truck/{foodTruckId}/menu", foodTruckId)
                        .header("USER_ID", Long.toString(userId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(foodTruckMenuRequest)))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        String response = mvcResult.getResponse().getContentAsString();
        CustomErrorResponse customErrorResponse = objectMapper.readValue(response, CustomErrorResponse.class);
        assertEquals(CustomErrorCode.ROLE_NOT_MATCH, customErrorResponse.errorType());
    }

    @Test
    @DisplayName("[POST] 푸드트럭 메뉴 등록 - 실패(본인 푸드트럭이 아님)")
    @Transactional
    void saveFoodTruckMenuFailureBecauseOfDifferentFoodTruck() throws Exception {
        // Given
        long userId = 6L;
        long foodTruckId = 2L;
        FoodTruckMenuRequest foodTruckMenuRequest = new FoodTruckMenuRequest("메뉴 이름", "메뉴 설명", "imageURl", 10000);

        // When
        MvcResult mvcResult = mockMvc.perform(post("/api/food-truck/{foodTruckId}/menu", foodTruckId)
                        .header("USER_ID", Long.toString(userId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(foodTruckMenuRequest)))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        String response = mvcResult.getResponse().getContentAsString();
        CustomErrorResponse customErrorResponse = objectMapper.readValue(response, CustomErrorResponse.class);
        assertEquals(CustomErrorCode.FOOD_TRUCK_IS_NOT_YOURS, customErrorResponse.errorType());
    }

    @Test
    @DisplayName("[POST] 푸드트럭 메뉴 등록 - 실패(푸드트럭이 존재하지 않음)")
    @Transactional
    void saveFoodTruckMenuFailureBecauseOfFoodTruckNotFound() throws Exception {
        // Given
        long userId = 6L;
        long foodTruckId = 100L;
        FoodTruckMenuRequest foodTruckMenuRequest = new FoodTruckMenuRequest("메뉴 이름", "메뉴 설명", "imageURl", 10000);

        // When
        MvcResult mvcResult = mockMvc.perform(post("/api/food-truck/{foodTruckId}/menu", foodTruckId)
                        .header("USER_ID", Long.toString(userId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(foodTruckMenuRequest)))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        String response = mvcResult.getResponse().getContentAsString();
        CustomErrorResponse customErrorResponse = objectMapper.readValue(response, CustomErrorResponse.class);
        assertEquals(CustomErrorCode.FOOD_TRUCK_NOT_FOUND, customErrorResponse.errorType());
    }

    @Test
    @DisplayName("[PATCH] 푸드트럭 메뉴 수정 - 성공")
    @Transactional
    void updateFoodTruckMenuSuccess() throws Exception {
        // Given
        long userId = 6L;
        long foodTruckId = 1L;
        long foodTruckMenuId = 1L;
        FoodTruckMenuRequest foodTruckMenuRequest = new FoodTruckMenuRequest("메뉴 이름", "메뉴 설명", "imageURl", 10000);

        // When
        MvcResult result = mockMvc.perform(post("/api/food-truck/{foodTruckId}/menu", foodTruckId)
                        .header("USER_ID", Long.toString(userId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(foodTruckMenuRequest)))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        FoodTruckMenu foodTruckMenu = objectMapper.readValue(result.getResponse().getContentAsString(), FoodTruckMenu.class);

        assertNotNull(foodTruckMenu.getId());
        assertEquals(foodTruckMenuRequest.name(), foodTruckMenu.getName());
        assertEquals(foodTruckMenuRequest.description(), foodTruckMenu.getDescription());
        assertEquals(foodTruckMenuRequest.imageUrl(), foodTruckMenu.getImageUrl());
        assertEquals(foodTruckMenuRequest.price(), foodTruckMenu.getPrice());
    }

    @Test
    @DisplayName("[DELETE] 푸드트럭 메뉴 삭제 - 성공")
    @Transactional
    void deleteFoodTruckMenuSuccess() throws Exception {
        // Given
        long userId = 6L;
        long foodTruckId = 1L;
        long foodTruckMenuId = 1L;

        // When
        mockMvc.perform(delete("/api/food-truck/{foodTruckId}/menu/{foodTruckMenuId}", foodTruckId, foodTruckMenuId)
                        .header("USER_ID", Long.toString(userId)))
                .andExpect(status().isOk());
    }
}
