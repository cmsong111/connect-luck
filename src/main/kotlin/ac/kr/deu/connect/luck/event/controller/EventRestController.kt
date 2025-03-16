package ac.kr.deu.connect.luck.event.controller

import ac.kr.deu.connect.luck.configuration.SpringDocConfig.Companion.EVENT
import ac.kr.deu.connect.luck.event.entity.Event
import ac.kr.deu.connect.luck.event.entity.EventStatus
import ac.kr.deu.connect.luck.event.service.EventService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedModel
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = EVENT)
@RestController
@RequestMapping("/api/v1/events")
class EventRestController(
    private val eventService: EventService,
) {
    @GetMapping
    fun getEvents(
        @RequestParam(required = false) keyword: String?,
        @RequestParam(required = false) status: EventStatus?,
        @ParameterObject pageable: Pageable,
    ): PagedModel<Event> {
        return PagedModel(
            eventService.getEvents(
                keyword = keyword,
                status = status,
                pageable = pageable
            )
        )
    }

    @GetMapping("/{eventId}")
    fun getEvent(
        @PathVariable eventId: Long,
    ): Event {
        return eventService.getEvent(eventId)
    }
}

