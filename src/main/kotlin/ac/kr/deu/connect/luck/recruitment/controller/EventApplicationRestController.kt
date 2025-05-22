package ac.kr.deu.connect.luck.recruitment.controller

import ac.kr.deu.connect.luck.configuration.SpringDocConfig.Companion.BEARER_AUTH
import ac.kr.deu.connect.luck.configuration.SpringDocConfig.Companion.EVENT_APPLICATION
import ac.kr.deu.connect.luck.configuration.SpringDocConfig.Companion.FOOD_TRUCK
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = EVENT_APPLICATION, description = "행사 신청 API")
@RestController
@RequestMapping("/api/v1/event/{eventId}/applications")
class EventApplicationRestController {
    @GetMapping
    @Operation(summary = "행사에 신청한 푸드트럭 목록 조회(행사 주최자용)")
    fun getEventApplications(
        @PathVariable eventId: Long,
        @AuthenticationPrincipal userDetails: UserDetails,
    ) {
        TODO("Not yet implemented")
    }

    @PostMapping
    @Operation(summary = "행사에 푸드트럭 신청 (푸드트럭 매니저용)")
    fun applyEvent(
        @PathVariable eventId: Long,
        @AuthenticationPrincipal userDetails: UserDetails,
    ) {
        TODO("Not yet implemented")
    }

    @PatchMapping("/{foodTruckId}")
    @Operation(summary = "행사 신청 상태 변경 (행사 주최자용)")
    fun updateApplicationStatus(
        @PathVariable eventId: Long,
        @PathVariable foodTruckId: Long,
        @AuthenticationPrincipal userDetails: UserDetails,
    ) {
        TODO("Not yet implemented")
    }

    @DeleteMapping("/{foodTruckId}")
    @Operation(summary = "행사 신청 취소 (푸드트럭 매니저용)")
    fun cancelApplication(
        @PathVariable eventId: Long,
        @PathVariable foodTruckId: Long,
        @AuthenticationPrincipal userDetails: UserDetails,
    ) {
        TODO("Not yet implemented")
    }
}

@Tag(name = FOOD_TRUCK)
@RestController
@RequestMapping("/api/v1/food-truck/{foodTruckId}/applications")
class EventApplicationFoodTruckRestController(

) {
    @GetMapping
    @Operation(summary = "푸드트럭이 신청한 행사 목록 조회")
    @SecurityRequirement(name = BEARER_AUTH)
    fun getFoodTruckApplications(
        @PathVariable foodTruckId: Long,
        @AuthenticationPrincipal userDetails: UserDetails,
    ) {
        TODO("Not yet implemented")
    }
}
