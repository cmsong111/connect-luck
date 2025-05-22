package ac.kr.deu.connect.luck.foodtruck.controller

import ac.kr.deu.connect.luck.common.controller.data.AuthenticatedUser
import ac.kr.deu.connect.luck.configuration.SpringDocConfig.Companion.BEARER_AUTH
import ac.kr.deu.connect.luck.configuration.SpringDocConfig.Companion.FOOD_TRUCK
import ac.kr.deu.connect.luck.foodtruck.controller.request.FoodTruckCreateForm
import ac.kr.deu.connect.luck.foodtruck.controller.request.FoodTruckMenuForm
import ac.kr.deu.connect.luck.foodtruck.controller.request.FoodTruckUpdateForm
import ac.kr.deu.connect.luck.foodtruck.entity.FoodTruckCategory
import ac.kr.deu.connect.luck.foodtruck.service.FoodTruckAdminService
import ac.kr.deu.connect.luck.foodtruck.service.FoodTruckService
import ac.kr.deu.connect.luck.foodtruck.service.data.FoodTruckData
import ac.kr.deu.connect.luck.foodtruck.service.data.FoodTruckSummaryData
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import java.net.URI
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.data.web.PagedModel
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = FOOD_TRUCK, description = "Food Truck API")
@RestController
@RequestMapping("/api/v1/food-trucks")
class FoodTruckRestController(
    private val foodTruckService: FoodTruckService,
    private val foodTruckAdminService: FoodTruckAdminService,
) {
    @GetMapping
    @Operation(summary = "푸드 트럭 전체 조회", description = "푸드트럭을 검색합니다.")
    fun getFoodTrucks(
        @RequestParam(required = false) @Parameter(description = "음식 종류") category: FoodTruckCategory?,
        @PageableDefault(size = 10, page = 0) @ParameterObject pageable: Pageable,
    ): ResponseEntity<PagedModel<FoodTruckSummaryData>> {
        return ResponseEntity.ok(
            PagedModel(
                foodTruckService.getFoodTrucks(
                    category = category,
                    pageable = pageable
                )
            )
        )
    }

    @GetMapping("/{foodTruckId}")
    @Operation(summary = "푸드트럭 정보를 조회합니다.")
    fun getFoodTruck(
        @PathVariable @Parameter(description = "푸드트럭 ID") foodTruckId: Long
    ): ResponseEntity<FoodTruckData> {
        return ResponseEntity.ok(
            foodTruckService.getFoodTruck(
                id = foodTruckId
            )
        )
    }

    @Operation(
        summary = "푸드트럭 등록",
        description = """<h1>신규 푸드트럭을 등록합니다.</h1>
            <p>푸드트럭 관리자만 가능합니다.</p>
            <h3>주의사항</h3>
            <ul>
                <li>name, description, foodType은 필수 항목입니다.</li>
                <li>이미지를 입력하지 않을 경우 기본 이미지가 등록됩니다.</li>
                <li>이미지를 비워둘 경우 Send Empty value를 체크 해재해주세요<br>빈 값 전송시 에러가 발생합니다.</li>
            </ul>
            <p>모두 Body의 Form Data로 전달해야 합니다.</p>
        """,
    )
    @SecurityRequirement(name = BEARER_AUTH)
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @PreAuthorize("hasRole('ROLE_FOOD_TRUCK_MANAGER')")
    fun createFoodTruck(
        @ModelAttribute foodTruckCreateForm: FoodTruckCreateForm,
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
    ): ResponseEntity<FoodTruckData> {
        val result: FoodTruckData = foodTruckAdminService.createFoodTruck(
            managerId = authenticatedUser.userId,
            foodTruckCreateForm = foodTruckCreateForm
        )
        return ResponseEntity.created(
            URI.create("/api/v1/food-trucks/${result.id}")
        ).body(result)
    }

    @Operation(
        summary = "통합 푸트트럭 정보 수정 (푸드트럭 관리자용)",
        description = """<h1>푸드트럭 정보를 수정합니다.</h1>
            <h2>푸트트럭 영업 정보 수정</h2>
            <ul>
                <li>latitude : 위도</li>
                <li>longitude : 경도</li>
                <li>status : 영업 상태 (Status.WORKING, Status.CLOSED)</li>
            </ul>
            <h2>푸트트럭 정보 수정</h2>
            <ul>
                <li>name : 상호명</li>
                <li>description : 설명</li>
                <li>foodType : 음식 종류</li>
                <li>phone : 전화번호</li>
                <li>address : 주소</li>
                <li>images : 이미지</li>
            </ul>
        """
    )
    @SecurityRequirement(name = BEARER_AUTH)
    @PatchMapping(value = ["/{foodTruckId}"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun updateFoodTruck(
        @Parameter(description = "푸드트럭 ID") @PathVariable foodTruckId: Long,
        @ModelAttribute foodTruckUpdateForm: FoodTruckUpdateForm,
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
    ): ResponseEntity<FoodTruckData> {
        return ResponseEntity.ok(
            foodTruckAdminService.updateFoodTruck(
                foodTruckId = foodTruckId,
                foodTruckUpdateForm = foodTruckUpdateForm,
            )
        )
    }

    @SecurityRequirement(name = BEARER_AUTH)
    @Operation(summary = "푸드트럭 메뉴 수정", description = "전부 교체되기 떄문에, 이전 데이터도 같이 보내주세요")
    @PatchMapping(value = ["/{foodTruckId}/menus"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun updateFoodTruckMenus(
        @Parameter(description = "푸드트럭 ID") @PathVariable foodTruckId: Long,
        @ModelAttribute menus: List<FoodTruckMenuForm>,
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
    ) {
        foodTruckAdminService.updateFoodTruck(
            foodTruckId = foodTruckId,
            foodTruckMenus = menus,
        )
    }

    @Operation(
        summary = "푸드트럭 정보 삭제",
        description = """<h1>푸드트럭 정보를 삭제합니다.</h1>
        <h3>삭제 되는 정보</h3>
        <ol>
            <li>푸드트럭 정보</li>
            <li>푸드트럭 메뉴</li>
            <li>푸드트럭 리뷰</li>
        </ol>
        """
    )
    @SecurityRequirement(name = BEARER_AUTH)
    @DeleteMapping("/{foodTruckId}")
    @PreAuthorize("hasRole('ROLE_FOOD_TRUCK_MANAGER')")
    fun deleteFoodTruck(
        @PathVariable foodTruckId: Long,
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
    ): ResponseEntity<String> {
        foodTruckAdminService.deleteFoodTruck(foodTruckId = foodTruckId)
        return ResponseEntity.noContent().build()
    }
}

