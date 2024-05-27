package ac.kr.deu.connect.luck.event.controller;

import ac.kr.deu.connect.luck.event.EventService;
import ac.kr.deu.connect.luck.event.EventStatus;
import ac.kr.deu.connect.luck.event.dto.EventRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public String getEvent(Model model) {
        model.addAttribute("events", eventService.getEvents((EventStatus) null)); //나중에 인자 신청 가능한 이벤트로 바꿔야함
        return "event/event-list";
    }

    @GetMapping("/my")
    public String getMyFoodTruck(Model model, Principal principal) {
        model.addAttribute("events", eventService.getEvents(principal.getName()));
        return "event/my_event_list";
    }

    @GetMapping("/{id}")
    public String getEventDetail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("event", eventService.getEvent(id));
        return "event/event-form";
    }

    @GetMapping("/register")
    public String getEventRegister(Model model) {
        return "event/event_register";
    }

    @PostMapping("/create")
    public String registerEventPost(
            Principal principal,
            EventRequest eventRequest, @RequestParam("multipartFile") MultipartFile multipartFile
    ){
        eventService.createEvent(eventRequest, multipartFile, principal.getName());
        return "redirect:/event/my";
    }

    @GetMapping("/delete/{id}")
    public String getEventDelete(@PathVariable("id") Long id, Principal principal) {
        eventService.deleteEvent(id, principal.getName());
        return "redirect:/event/my";
    }

    @GetMapping("/edit/{id}")
    public String getEventUpdate(@PathVariable("id") Long id, Model model) {
        model.addAttribute("event", eventService.getEvent(id));
        return "event/event_update";
    }

    @PostMapping("/update")
    public String updateEventPost(
            @RequestParam("eventId") Long id,
            Principal principal,
            EventRequest eventRequest,
            @RequestParam("multipartFile") MultipartFile multipartFile
    ){
        eventService.updateEvent(id, eventRequest, multipartFile, principal.getName());
        return "redirect:/event/my";
    }
    @PostMapping("/statusUpdate/{id}")
    public String updateEventPost(
            @PathVariable("id") Long id,
            Principal principal,
            @RequestParam("status") EventStatus eventStatus
    ){
        eventService.changeEventStatus(id, eventStatus, principal.getName());
        return "redirect:/event/my";
    }
}
