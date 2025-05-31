package ac.kr.deu.connect.luck.recruitment.controller

import ac.kr.deu.connect.luck.recruitment.service.FoodTruckRecruitmentService
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam


@Controller
@RequestMapping("/recruitments")
class EventApplicationController(
    private val foodTruckRecruitmentService: FoodTruckRecruitmentService
) {
    /**
     * 모집 목록 페이지로 이동합니다.
     */
    @GetMapping
    fun recruitmentList(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "10") size: Int,
        model: Model,
    ): String {
        model.addAttribute(
            "recruitmentList",
            foodTruckRecruitmentService.getRecruitmentList(
                page = page,
                size = size,
            )
        )
        return "recruitment/list"
    }

    /**
     * 모집 상세 페이지로 이동합니다.
     */
    @GetMapping("/{recruitmentId}")
    fun recruitmentDetail(
        @PathVariable("recruitmentId") recruitmentId: Long,
        model: Model,
    ): String {
        model.addAttribute(
            "recruitment",
            foodTruckRecruitmentService.getRecruitment(recruitmentId).apply {
                this.description = HtmlRenderer.builder().build().render(
                    Parser.builder().build().parse(this.description)
                )
            }
        )
        model.addAttribute(
            "applicationCount",
            foodTruckRecruitmentService.countRecruitmentApplications(recruitmentId)
        )
        return "recruitment/detail"
    }

    /**
     * 모집 신청 리스트 페이지로 이동합니다.
     */
    @GetMapping("/{recruitmentId}/applications")
    fun recruitmentApplications(
        @PathVariable("recruitmentId") recruitmentId: Long,
        model: Model,
    ): String {
        model.addAttribute(
            "recruitment",
            foodTruckRecruitmentService.getRecruitment(recruitmentId)
        )
        model.addAttribute(
            "applications",
            foodTruckRecruitmentService.getRecruitmentApplications(recruitmentId)
        )
        return "recruitment/applications-list"
    }

    /**
     * 신청 페이지로 이동합니다.
     */
    @GetMapping("/{recruitmentId}/apply")
    fun recruitmentApply(
        @PathVariable("recruitmentId") recruitmentId: Long,
        model: Model,
    ): String {
        TODO("신청 페이지 구현")
    }

    /**
     * 신청 성공 페이지로 이동합니다.
     */
    @GetMapping("/{recruitmentId}/apply/success")
    fun recruitmentApplySuccess(
        @PathVariable("recruitmentId") recruitmentId: Long,
        model: Model,
    ): String {
        TODO("신청 성공 페이지 구현")
    }
}
