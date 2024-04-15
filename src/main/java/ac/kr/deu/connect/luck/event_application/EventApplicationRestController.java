package ac.kr.deu.connect.luck.event_application;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class EventApplicationRestController {

    private final EventApplicationService eventApplicationService;

    @PostMapping("event/{id}/apply")
    public void applyEvent(
            @PathVariable(name = "id") Long id,
            @RequestBody EventApplicationRequest eventApplicationRequest,
            @RequestHeader(name = "username") String username
    ) {
        log.info("requestbody : {}",eventApplicationRequest.comment());
        eventApplicationService.createEventApplication(eventApplicationRequest, id, username);
    }

    @GetMapping("event/apply")
    public List<EventApplication> getEventApplications(){
        return eventApplicationService.getEventApplications();
    }
}
