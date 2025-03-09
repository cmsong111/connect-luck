package ac.kr.deu.connect.luck.event.application;

import ac.kr.deu.connect.luck.common.entity.AuthenticatedUser;
import ac.kr.deu.connect.luck.event.service.EventService;
import ac.kr.deu.connect.luck.food_truck.service.FoodTruckService;
import ac.kr.deu.connect.luck.user.entity.User;
import ac.kr.deu.connect.luck.user.service.UserService;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/apply")
@RequiredArgsConstructor
public class EventApplicationController {

    private final EventService eventService;
    private final UserService userService;
    private final FoodTruckService foodTruckService;
    private final EventApplicationService eventApplicationService;

    @GetMapping("/{id}")
    public String getEventDetail(
            @PathVariable("id") Long id,
            Model model,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser
    ) {
        model.addAttribute("event", eventService.getEvent(id));
        model.addAttribute("user", userService.findByEmail(authenticatedUser.getEmail()));
        return "event/event-apply";
    }

    @GetMapping("/list/{id}")
    public String getApplyList(
            @PathVariable("id") Long id,
            Model model,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser
    ) {
        List<EventApplication> applications = eventApplicationService.getEventApplications(id);

        model.addAttribute("user", userService.findByEmail(authenticatedUser.getEmail()));
        model.addAttribute("applications", applications);
        model.addAttribute("foodTrucks", applications.stream()
                .map(app -> foodTruckService.getFoodTruck(app.getFoodTruckId()))
                .collect(Collectors.toList()));

        return "event/my_apply_list";
    }

    @GetMapping("/accept/{id}")
    public String getApplicationAccept(@PathVariable("id") Long id, @RequestParam("eventId") Long eventId, Model model, Principal principal) {
        eventApplicationService.approveEventApplication(eventId, id, ApplicationStatus.APPROVED);
        return "event/my_apply_list";
    }

    @GetMapping("/reject/{id}")
    public String getApplicationReject(@PathVariable("id") Long id, @RequestParam("eventId") Long eventId, Model model, Principal principal) {
        eventApplicationService.approveEventApplication(eventId, id, ApplicationStatus.REJECTED);
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
