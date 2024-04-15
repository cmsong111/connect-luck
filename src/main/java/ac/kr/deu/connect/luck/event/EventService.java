package ac.kr.deu.connect.luck.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public List<Event> getEvents() {
        return eventRepository.findAll();
    }

    public Event getEvent(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    public Event createEvent(EventRequest eventRequest) {
        return eventRepository.save(eventMapper.toEvent(eventRequest));
    }

    public Event updateEvent(Long id, EventRequest eventRequest) {
        Event findEvent = eventRepository.findById(id).orElse(null);
        if (findEvent == null) {
            return null;
        }
        findEvent.setTitle(eventRequest.title());
        findEvent.setContent(eventRequest.content());
        findEvent.setStartAt(eventRequest.startAt());
        findEvent.setEndAt(eventRequest.endAt());
        return eventRepository.save(findEvent);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}
