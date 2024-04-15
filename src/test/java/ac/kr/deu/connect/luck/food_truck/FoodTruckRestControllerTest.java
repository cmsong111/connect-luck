package ac.kr.deu.connect.luck.food_truck;

import ac.kr.deu.connect.luck.exception.CustomErrorCode;
import ac.kr.deu.connect.luck.exception.CustomErrorResponse;
import ac.kr.deu.connect.luck.exception.CustomException;
import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckRequest;
import ac.kr.deu.connect.luck.food_truck.entity.FoodTruck;
import ac.kr.deu.connect.luck.food_truck.entity.FoodType;
import ac.kr.deu.connect.luck.food_truck.repository.FoodTruckRepository;
import ac.kr.deu.connect.luck.user.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FoodTruckRestControllerTest {


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FoodTruckRepository foodTruckRepository;

    @Autowired
    private FoodTruckMapper mapStructMapper;


    @Test
    @DisplayName("[POST] 푸드트럭 신규 생성 - 성공")
    @Transactional
    void createFoodTruckSuccess() throws Exception {
        // Given
        FoodTruckRequest foodTruckRequest = new FoodTruckRequest("푸드트럭1", "서울시 강남구에서 제일 잘나가는 식당", "https://picsum.photos/1600/900", FoodType.DESSERT);
        Long userId = 8L;

        // When
        MvcResult mvcResult = mockMvc.perform(post("/api/food-truck")
                        .header("USER_ID", String.valueOf(userId))
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
        MvcResult mvcResult = mockMvc.perform(post("/api/food-truck")
                        .header("USER_ID", String.valueOf(userId))
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
                        .header("USER_ID", String.valueOf(userId))
                        .content(objectMapper.writeValueAsString(foodTruckRequest))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        CustomErrorResponse customErrorResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CustomErrorResponse.class);
        assertEquals(CustomErrorCode.ROLE_NOT_MATCH, customErrorResponse.errorType());
    }

    @Test
    @DisplayName("[PATCH] 푸드트럭 정보 수정 - 성공")
    @Transactional
    void updateFoodTruckSuccess() throws Exception {
        // Given
        FoodTruck originFoodTruck = foodTruckRepository.findById(1L).orElseThrow(
                () -> new CustomException(CustomErrorCode.FOOD_TRUCK_NOT_FOUND)
        );

        originFoodTruck.setName("수정된 푸드트럭");
        originFoodTruck.setDescription("수정된 푸드트럭입니다.");
        originFoodTruck.setImageUrl("https://picsum.photos/1600/900");
        originFoodTruck.setFoodType(FoodType.ETC);

        User user = originFoodTruck.getManager();

        FoodTruckRequest foodTruckRequest = mapStructMapper.toFoodTruckRequest(originFoodTruck);

        // When
        MvcResult mvcResult = mockMvc.perform(patch("/api/food-truck/" + originFoodTruck.getId())
                        .header("USER_ID", String.valueOf(user.getId()))
                        .content(objectMapper.writeValueAsString(foodTruckRequest))
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        FoodTruck responseFoodTruck = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), FoodTruck.class);

        assertEquals(originFoodTruck.getName(), responseFoodTruck.getName());
        assertEquals(originFoodTruck.getDescription(), responseFoodTruck.getDescription());
        assertEquals(originFoodTruck.getImageUrl(), responseFoodTruck.getImageUrl());
        assertEquals(originFoodTruck.getFoodType(), responseFoodTruck.getFoodType());
    }

    @Test
    @DisplayName("[PATCH] 푸드트럭 정보 수정 - 실패(푸드트럭이 존재하지 않음)")
    @Transactional
    void updateFoodTruckFailNotFound() throws Exception {
        // Given
        FoodTruck originFoodTruck = foodTruckRepository.findById(1L).orElseThrow(
                () -> new CustomException(CustomErrorCode.FOOD_TRUCK_NOT_FOUND)
        );

        originFoodTruck.setName("수정된 푸드트럭");
        originFoodTruck.setDescription("수정된 푸드트럭입니다.");
        originFoodTruck.setImageUrl("https://picsum.photos/1600/900");
        originFoodTruck.setFoodType(FoodType.ETC);

        User user = originFoodTruck.getManager();

        FoodTruckRequest foodTruckRequest = mapStructMapper.toFoodTruckRequest(originFoodTruck);

        // When
        MvcResult mvcResult = mockMvc.perform(patch("/api/food-truck/100")
                        .header("USER_ID", String.valueOf(user.getId()))
                        .content(objectMapper.writeValueAsString(foodTruckRequest))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        CustomErrorResponse customErrorResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CustomErrorResponse.class);
        assertEquals(CustomErrorCode.FOOD_TRUCK_NOT_FOUND, customErrorResponse.errorType());
    }

    @Test
    @DisplayName("[PATCH] 푸드트럭 정보 수정 - 실패(유저가 푸드트럭 매니저가 아님)")
    @Transactional
    void updateFoodTruckFailNotManager() throws Exception {
        // Given
        FoodTruck originFoodTruck = foodTruckRepository.findById(1L).orElseThrow(
                () -> new CustomException(CustomErrorCode.FOOD_TRUCK_NOT_FOUND)
        );

        FoodTruckRequest foodTruckRequest = mapStructMapper.toFoodTruckRequest(originFoodTruck);

        // When
        MvcResult mvcResult = mockMvc.perform(patch("/api/food-truck/" + originFoodTruck.getId())
                        .header("USER_ID", "1")
                        .content(objectMapper.writeValueAsString(foodTruckRequest))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        CustomErrorResponse customErrorResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CustomErrorResponse.class);
        assertEquals(CustomErrorCode.ROLE_NOT_MATCH, customErrorResponse.errorType());
    }

    @Test
    @DisplayName("[PATCH] 푸드트럭 정보 수정 - 실패(다른 유저의 푸드트럭 수정)")
    @Transactional
    void updateFoodTruckFailNotYours() throws Exception {
        // Given
        FoodTruck originFoodTruck = foodTruckRepository.findById(1L).orElseThrow(
                () -> new CustomException(CustomErrorCode.FOOD_TRUCK_NOT_FOUND)
        );

        FoodTruckRequest foodTruckRequest = mapStructMapper.toFoodTruckRequest(originFoodTruck);

        // When
        MvcResult mvcResult = mockMvc.perform(patch("/api/food-truck/" + originFoodTruck.getId())
                        .header("USER_ID", "7")
                        .content(objectMapper.writeValueAsString(foodTruckRequest))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Then
        CustomErrorResponse customErrorResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CustomErrorResponse.class);
        assertEquals(CustomErrorCode.FOOD_TRUCK_IS_NOT_YOURS, customErrorResponse.errorType());
    }

    @Test
    @DisplayName("[GET] 푸드트럭 조회")
    void getFoodTruck() throws Exception {
        // When
        MvcResult mvcResult = mockMvc.perform(get("/api/food-truck"))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        List<FoodTruck> foodTruck = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<FoodTruck>>() {
        });
        assertNotNull(foodTruck);
    }

    @Test
    @DisplayName("[GET] 푸드트럭 조회 - 음식 종류 지정")
    void getFoodTruckByFoodType() throws Exception {
        // Given
        FoodType foodType = FoodType.DESSERT;

        // When
        MvcResult mvcResult = mockMvc.perform(get("/api/food-truck")
                        .param("foodType", foodType.name()))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        List<FoodTruck> foodTruck = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<FoodTruck>>() {
        });

        for (FoodTruck truck : foodTruck) {
            assertEquals(foodType, truck.getFoodType());
        }
    }

    @Test
    @DisplayName("[GET] 푸드트럭 조회 - 상호명 지정")
    void getFoodTruckByName() throws Exception {
        // Given
        String name = "청년";

        // When
        MvcResult mvcResult = mockMvc.perform(get("/api/food-truck")
                        .param("name", name))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        List<FoodTruck> foodTruck = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<FoodTruck>>() {
        });

        for (FoodTruck truck : foodTruck) {
            assertTrue(truck.getName().contains(name));
        }
    }

    @Test
    @DisplayName("[GET] 푸드트럭 조회 - 상호명, 음식 종류 지정")
    void getFoodTruckByNameAndFoodType() throws Exception {
        // Given
        String name = "청년";
        FoodType foodType = FoodType.BURGER;

        // When
        MvcResult mvcResult = mockMvc.perform(get("/api/food-truck")
                        .param("name", name)
                        .param("foodType", foodType.name()))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        List<FoodTruck> foodTruck = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<FoodTruck>>() {
        });

        for (FoodTruck truck : foodTruck) {
            assertTrue(truck.getName().contains(name));
            assertEquals(foodType, truck.getFoodType());
        }
    }


}
