package ac.kr.deu.connect.luck.event_application;

import javax.annotation.processing.Generated;

import org.springframework.stereotype.Component;

@Generated(
        value = "org.mapstruct.ap.MappingProcessor",
        date = "2024-05-14T13:29:17+0900",
        comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class EventApplicationMapperImpl implements EventApplicationMapper {

    @Override
    public EventApplication toEventApplication(EventApplicationRequest eventApplicationRequest) {
        if (eventApplicationRequest == null) {
            return null;
        }

        EventApplication.EventApplicationBuilder eventApplication = EventApplication.builder();

        eventApplication.comment(eventApplicationRequest.comment());

        return eventApplication.build();
    }
}
