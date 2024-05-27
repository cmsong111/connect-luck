package ac.kr.deu.connect.luck.event;

import ac.kr.deu.connect.luck.event.dto.EventDetailResponse;
import ac.kr.deu.connect.luck.event.dto.EventRequest;
import ac.kr.deu.connect.luck.user.User;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-14T13:29:17+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class EventMapperImpl implements EventMapper {

    @Override
    public Event toEvent(EventRequest eventRequest) {
        if ( eventRequest == null ) {
            return null;
        }

        Event.EventBuilder event = Event.builder();

        event.title( eventRequest.title() );
        event.content( eventRequest.content() );
        event.zipCode( eventRequest.zipCode() );
        event.streetAddress( eventRequest.streetAddress() );
        event.detailAddress( eventRequest.detailAddress() );
        event.startAt( eventRequest.startAt() );
        event.endAt( eventRequest.endAt() );

        return event.build();
    }

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
