package ac.kr.deu.connect.luck.event.controller;

import ac.kr.deu.connect.luck.event.EventService;
import ac.kr.deu.connect.luck.event.EventStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.security.Principal;

@Controller
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public String getEvent(Model model) {
        model.addAttribute("events", eventService.getEvents((EventStatus) null));
        return "event/event-list";
    }

    @GetMapping("/my")
    public String getMyFoodTruck(Model model, Principal principal) {
        model.addAttribute("events", eventService.getEvents(principal.getName()));
        return "event/my_event_list";
    }

    //자세히 보기 페이지 매핑 메서드이나 자세히 보기를 사용하지 않을 예정, event-form 구현 안함
    @GetMapping("/{id}")
    public String getEventDetail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("event", eventService.getEvent(id));
        return "event/event-form";
    }

}
