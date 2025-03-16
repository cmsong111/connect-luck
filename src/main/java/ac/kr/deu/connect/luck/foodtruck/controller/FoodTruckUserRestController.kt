package ac.kr.deu.connect.luck.foodtruck.controller

import ac.kr.deu.connect.luck.configuration.SpringDocConfig.Companion.USER
import ac.kr.deu.connect.luck.foodtruck.service.FoodTruckService
import ac.kr.deu.connect.luck.foodtruck.service.data.FoodTruckSummaryData
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.data.web.PagedModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = USER)
@RestController
@RequestMapping("/api/v1/users")
class FoodTruckUserRestController(
    val foodTruckService: FoodTruckService
) {
    @Operation(
        summary = "사용자의 푸드트럭 정보를 조회합니다.",
        description = "사용자의 푸드트럭 정보를 조회합니다.",
    )
    @GetMapping("/{userId}/food-trucks")
    fun getFoodTrucks(
        @PathVariable userId: Long,
        @PageableDefault(size = 10, page = 0) @ParameterObject pageable: Pageable,
    ): ResponseEntity<PagedModel<FoodTruckSummaryData>> {
        return ResponseEntity.ok(
            PagedModel(
                foodTruckService.getFoodTrucks(
                    managerId = userId,
                    pageable = pageable,
                )
            )
        )
    }
}

