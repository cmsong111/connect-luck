package ac.kr.deu.connect.luck.event.controller;

import ac.kr.deu.connect.luck.event.EventService;
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
        model.addAttribute("events", eventService.getEvents(null));
        return "event/event-list";
    }

    @GetMapping("/my")
    public String getMyFoodTruck(Model model, Principal principal) {

        return "event/my_event_list";
    }

    @GetMapping("/{id}")
    public String getEventDetail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("event", eventService.getEvent(id));
        return "event/event-form";
    }

}
