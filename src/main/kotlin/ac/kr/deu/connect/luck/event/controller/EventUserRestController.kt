package ac.kr.deu.connect.luck.event.controller

import ac.kr.deu.connect.luck.configuration.SpringDocConfig.Companion.USER
import ac.kr.deu.connect.luck.event.entity.Event
import ac.kr.deu.connect.luck.event.service.EventService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedModel
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = USER)
@RestController
@RequestMapping("/api/v1/users/{userId}")
class EventUserRestController(
    private val eventService: EventService,
) {
    @GetMapping("/events")
    @Operation(summary = "행사 매니저의 행사 목록 조회")
    fun getUsers(
        @PathVariable userId: Long,
    ): PagedModel<Event> {
        return PagedModel(
            eventService.getEvents(
                managerId = userId,
                pageable = Pageable.unpaged()
            )
        )
    }
}
