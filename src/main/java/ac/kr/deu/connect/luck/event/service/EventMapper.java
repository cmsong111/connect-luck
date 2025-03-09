package ac.kr.deu.connect.luck.event.service;

import ac.kr.deu.connect.luck.event.controller.response.EventDetailResponse;
import ac.kr.deu.connect.luck.event.controller.request.EventRequest;
import ac.kr.deu.connect.luck.event.controller.request.EventRequestV2;
import ac.kr.deu.connect.luck.event.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventMapper {
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
