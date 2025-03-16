package ac.kr.deu.connect.luck.event.controller

import ac.kr.deu.connect.luck.common.controller.data.AuthenticatedUser
import ac.kr.deu.connect.luck.configuration.SpringDocConfig.Companion.BEARER_AUTH
import ac.kr.deu.connect.luck.configuration.SpringDocConfig.Companion.EVENT
import ac.kr.deu.connect.luck.event.entity.Event
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = EVENT)
@RestController
@RequestMapping("/api/v1/events")
@SecurityRequirement(name = BEARER_AUTH)
class EventAdminRestController {
    @PostMapping
    @Operation(summary = "새 행사 생성")
    fun createEvent(
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser
    ): Event {
        TODO("Not yet implemented")
    }

    @PatchMapping("/{eventId}")
    @Operation(summary = "이벤트 수정")
    fun updateEvent(
        @PathVariable eventId: Long,
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser
    ): Event {
        TODO("Not yet implemented")
    }

    @DeleteMapping("/{eventId}")
    @Operation(summary = "이벤트 삭제")
    fun deleteEvent(
        @PathVariable eventId: Long,
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser
    ): Event {
        TODO("Not yet implemented")
    }
}
