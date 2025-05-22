package ac.kr.deu.connect.luck.event.controller

import ac.kr.deu.connect.luck.event.service.EventService
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/events")
class EventController(
    private val eventService: EventService
) {

    @GetMapping
    fun getEvents(
        model: Model,
        @PageableDefault(size = 10, page = 0) @ParameterObject pageable: Pageable,
    ): String {
        model.addAttribute(
            "events", eventService.getEvents(
                pageable = pageable,
            )
        )
        return "event/event-list"
    }

    @GetMapping("/{eventId}")
    fun getEvent(
        model: Model,
        @PathVariable eventId: Long
    ): String {
        model.addAttribute(
            "event", eventService.getEvent(eventId)
        )
        return "event/event-form"
    }
}
