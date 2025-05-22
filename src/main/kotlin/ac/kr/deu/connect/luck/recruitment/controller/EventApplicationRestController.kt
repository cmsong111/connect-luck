package ac.kr.deu.connect.luck.recruitment.controller

import ac.kr.deu.connect.luck.configuration.SpringDocConfig.Companion.BEARER_AUTH
import ac.kr.deu.connect.luck.configuration.SpringDocConfig.Companion.FOOD_TRUCK
import ac.kr.deu.connect.luck.configuration.SpringDocConfig.Companion.RECRUITMENT
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
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = RECRUITMENT, description = "행사 신청 API")
@RestController
@RequestMapping("/api/v1/recruitments")
class EventApplicationRestController {
    @GetMapping
    @Operation(summary = "푸드트럭 모집 공고 목록 조회")
    fun getRecruitmentList(
        @RequestParam eventId: Long,
        @AuthenticationPrincipal userDetails: UserDetails,
    ) {
        TODO("Not yet implemented")
    }

    @PostMapping
    @Operation(summary = "푸드트럭 모집 공고 생성")
    fun createRecruitment(
        @AuthenticationPrincipal userDetails: UserDetails,
    ) {
        TODO("Not yet implemented")
    }

    @GetMapping("/{recruitmentId}")
    @Operation(summary = "푸드트럭 모집 공고 상세 조회")
    fun getRecruitment(
        @PathVariable recruitmentId: Long,
        @AuthenticationPrincipal userDetails: UserDetails,
    ) {
        TODO("Not yet implemented")
    }

    @PatchMapping("/{recruitmentId}")
    @Operation(summary = "푸드트럭 모집 공고 수정")
    fun updateApplicationStatus(
        @PathVariable recruitmentId: Long,
        @AuthenticationPrincipal userDetails: UserDetails,
    ) {
        TODO("Not yet implemented")
    }

    @DeleteMapping("/{recruitmentId}")
    @Operation(summary = "행사 신청 취소 (푸드트럭 매니저용)")
    fun cancelApplication(
        @PathVariable recruitmentId: Long,
        @AuthenticationPrincipal userDetails: UserDetails,
    ) {
        TODO("Not yet implemented")
    }
}

@Tag(name = FOOD_TRUCK)
@RestController
@RequestMapping("/api/v1/food-trucks/{foodTruckId}/recruitments")
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
