package ac.kr.deu.connect.luck.food_truck.controller

import ac.kr.deu.connect.luck.exception.CustomErrorResponse
import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckDetailResponse
import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckHeader
import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckRequestV2
import ac.kr.deu.connect.luck.food_truck.entity.FoodType
import ac.kr.deu.connect.luck.food_truck.service.FoodTruckService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.security.Principal

@Tag(name = "03-Food Truck", description = "Food Truck API")
@RestController
@RequestMapping("/api/food-truck")
class FoodTruckRestController(
    val foodTruckService: FoodTruckService
) {
    @GetMapping
    @Operation(summary = "푸드 트럭 검색", description = "푸드트럭을 검색합니다.")
    fun getFoodTrucks(
        @Parameter(description = "상호명") @RequestParam(name = "name", required = false) name: String?,
        @Parameter(description = "음식 종류") @RequestParam(name = "foodType", required = false) foodType: FoodType?
    ): ResponseEntity<List<FoodTruckHeader>> {
        return ResponseEntity.ok(foodTruckService.getFoodTrucks(name, foodType))
    }

    @GetMapping("/{id}")
    @Operation(summary = "푸드트럭 정보를 조회합니다.")
    fun getFoodTruck(
        @Parameter(description = "푸드트럭 UID") @PathVariable("id") id: Long
    ): ResponseEntity<FoodTruckDetailResponse> {
        return ResponseEntity.ok(foodTruckService.getFoodTruck(id))
    }


    @Operation(
        summary = "푸드트럭 등록",
        description = "<h1>신규 푸드트럭을 등록합니다.</h1><b>푸드트럭 관리자만 가능합니다.</b><h3>주의사항</h3><ul><li>name, description, foodType은 필수 항목입니다.</li><li>이미지를 입력하지 않을 경우 기본 이미지가 등록됩니다.</li><li>이미지를 비워둘 경우 Send Empty value를 체크 해재해주세요<br>빈 값 전송시 에러가 발생합니다.</li></ul><br>모두 Body의 Form Data로 전달해야 합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "푸드트럭 등록 성공",
                content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = FoodTruckDetailResponse::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "푸드트럭 등록 실패",
                content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = CustomErrorResponse::class))]
            ),
        ]
    )
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @PreAuthorize("hasRole('ROLE_FOOD_TRUCK_MANAGER')")
    fun createFoodTruck(
        @ModelAttribute foodTruckRequestV2: FoodTruckRequestV2, principal: Principal
    ): ResponseEntity<FoodTruckDetailResponse> {
        val result: FoodTruckDetailResponse = foodTruckService.saveFoodTruck(principal.name, foodTruckRequestV2)
        return ResponseEntity.created(URI.create("/api/food-truck/${result.id}")).body(result)
    }

    @Operation(
        summary = "푸드트럭 정보를 업데이트 합니다.", description = "푸드트럭 정보를 업데이트 합니다. 해당 푸드트럭 관리자만 가능합니다.<br>값을 변경하지 않더라도 값을 꼭 넣어야 합니다. (이미지는 예외)"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "푸드트럭 정보 업데이트 성공",
                content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = FoodTruckDetailResponse::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "푸드트럭 정보 업데이트 실패",
                content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = CustomErrorResponse::class))]
            ),
        ]
    )
    @PatchMapping(value = ["/{id}"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @PreAuthorize("hasRole('ROLE_FOOD_TRUCK_MANAGER') and @checker.isFoodTruckManager(#truckId)")
    fun updateFoodTruck(
        @Parameter(description = "푸드트럭 ID") @PathVariable("id") truckId: Long,
        @ModelAttribute foodTruckRequestV2: FoodTruckRequestV2,
        principal: Principal
    ): ResponseEntity<FoodTruckDetailResponse> {
        return ResponseEntity.ok(foodTruckService.saveFoodTruck(truckId, foodTruckRequestV2))
    }

    @Operation(
        summary = "푸드트럭 정보 삭제", description = "<h1>푸드트럭 정보를 삭제합니다.</h1><h3>삭제 되는 정보</h3><ol><li>푸드트럭 정보</li><li>푸드트럭 메뉴</li><li>푸드트럭 리뷰</li></ol>"
    )
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "204",
            description = "푸드트럭 정보 삭제 성공",
            content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = String::class))]
        ), ApiResponse(
            responseCode = "403",
            description = "푸드트럭 정보 삭제 실패 (권한 없음)",
            content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = CustomErrorResponse::class))]
        )]
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_FOOD_TRUCK_MANAGER') and @checker.isFoodTruckManager(#truckId)")
    fun deleteFoodTruck(
        @PathVariable id: Long
    ): ResponseEntity<String> {
        foodTruckService.deleteFoodTruck(id)
        return ResponseEntity.noContent().build()
    }

    @Operation(
        summary = "내 푸드트럭 정보를 조회합니다.", description = "내 푸드트럭 정보를 조회합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "내 푸드트럭 정보 조회 성공",
                content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = FoodTruckDetailResponse::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "내 푸드트럭 정보 조회 실패 (권한 없음)",
                content = [Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = CustomErrorResponse::class))]
            )
        ]
    )
    @GetMapping("/my")
    fun getMyFoodTruck(principal: Principal): ResponseEntity<List<FoodTruckDetailResponse>> {
        return ResponseEntity.ok(foodTruckService.getMyFoodTrucksForApi(principal.name));
    }
}
