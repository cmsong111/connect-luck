package ac.kr.deu.connect.luck.event_application;
import ac.kr.deu.connect.luck.event.EventService;
import ac.kr.deu.connect.luck.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.security.Principal;

@Controller
@RequestMapping("/apply")
@RequiredArgsConstructor
public class EventApplicationController {

    private final EventService eventService;
    private final UserService userService;
    private final EventApplicationService eventApplicationService;

    @GetMapping("/{id}")
    public String getEventDetail(@PathVariable("id") Long id, Model model, Principal principal) {
        model.addAttribute("event", eventService.getEvent(id));
        model.addAttribute("user", userService.findUserByEmail(principal.getName()));
        return "event/event-apply";
    }

    @PostMapping("/create")
    public String registerFoodTruckPost(
            Principal principal,
            EventApplicationRequest eventApplicationRequest
    ){
        eventApplicationService.createEventApplication(eventApplicationRequest, principal.getName());
        return "redirect:/event";
    }
}
