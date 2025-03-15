package ac.kr.deu.connect.luck.event.service;

import ac.kr.deu.connect.luck.event.controller.request.EventRequestV2;
import ac.kr.deu.connect.luck.event.controller.response.EventDetailResponse;
import ac.kr.deu.connect.luck.event.entity.Event;
import ac.kr.deu.connect.luck.user.entity.User;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-15T23:21:04+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class EventMapperImpl implements EventMapper {

    @Override
    public EventDetailResponse toEventResponse(Event event) {
        if ( event == null ) {
            return null;
        }

        String managerName = null;
        Long id = null;
        String title = null;
        String content = null;
        String zipCode = null;
        String streetAddress = null;
        String detailAddress = null;
        LocalDateTime startAt = null;
        LocalDateTime endAt = null;
        String imageUrl = null;
        String status = null;

        managerName = eventManagerName( event );
        id = event.getId();
        title = event.getTitle();
        content = event.getContent();
        zipCode = event.getZipCode();
        streetAddress = event.getStreetAddress();
        detailAddress = event.getDetailAddress();
        startAt = event.getStartAt();
        endAt = event.getEndAt();
        imageUrl = event.getImageUrl();
        if ( event.getStatus() != null ) {
            status = event.getStatus().name();
        }

        EventDetailResponse eventDetailResponse = new EventDetailResponse( id, title, content, zipCode, streetAddress, detailAddress, startAt, endAt, imageUrl, managerName, status );

        return eventDetailResponse;
    }

    @Override
    public Event toEvent(EventRequestV2 eventRequest) {
        if ( eventRequest == null ) {
            return null;
        }

        Event.EventBuilder event = Event.builder();

        event.title( eventRequest.getTitle() );
        event.content( eventRequest.getContent() );
        event.zipCode( eventRequest.getZipCode() );
        event.streetAddress( eventRequest.getStreetAddress() );
        event.detailAddress( eventRequest.getDetailAddress() );
        event.startAt( eventRequest.getStartAt() );
        event.endAt( eventRequest.getEndAt() );

        return event.build();
    }

    private String eventManagerName(Event event) {
        if ( event == null ) {
            return null;
        }
        User manager = event.getManager();
        if ( manager == null ) {
            return null;
        }
        String name = manager.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
