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

    //푸드트럭 id 이용해서 푸드트럭 사장 정보에서 푸드트럭 id에 맞는 푸드트럭 빼와서 푸드트럭 이미지랑 그런거 보여줘여할듯 아직 미구현
    @GetMapping("/list/{id}")
    public String getApplyList(@PathVariable("id") Long id, Model model, Principal principal) {
        model.addAttribute("user", userService.findUserByEmail(principal.getName()));
        model.addAttribute("applications", eventApplicationService.getEventApplications(id));
        return "event/my_apply_list";
    }

    @PostMapping("/create")
    public String registerFoodTruckPost(
            Principal principal,
            EventApplicationRequest eventApplicationRequest
    ) {
        eventApplicationService.createEventApplication(eventApplicationRequest, principal.getName());
        return "redirect:/event";
    }
}
