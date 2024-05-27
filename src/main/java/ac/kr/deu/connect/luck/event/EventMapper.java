package ac.kr.deu.connect.luck.event;

import ac.kr.deu.connect.luck.event.dto.EventDetailResponse;
import ac.kr.deu.connect.luck.event.dto.EventRequest;
import ac.kr.deu.connect.luck.event.dto.EventRequestV2;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventMapper {
    /**
     * EventRequest를 Event로 변환
     *
     * @param eventRequest EventRequest
     * @return Event
     */
    Event toEvent(EventRequest eventRequest);

    /**
     * Event를 EventResponse로 변환
     *
     * @param event Event
     * @return EventResponse
     */
    @Mapping(source = "manager.name", target = "managerName")
    EventDetailResponse toEventResponse(Event event);

    Event toEvent(EventRequestV2 eventRequest);
}
