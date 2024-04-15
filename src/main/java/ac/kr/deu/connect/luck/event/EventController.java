package ac.kr.deu.connect.luck.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public String getEvent(Model model) {
        model.addAttribute("events", eventService.getEvents());
        return "event/event-list";
    }

    @GetMapping("/{id}")
    public String getEventDetail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("event", eventService.getEvent(id));
        return "event/event-form";
    }

}
