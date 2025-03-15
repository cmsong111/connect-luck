package ac.kr.deu.connect.luck.event.application;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-15T23:21:04+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class EventApplicationMapperImpl implements EventApplicationMapper {

    @Override
    public EventApplication toEventApplication(EventApplicationRequest eventApplicationRequest) {
        if ( eventApplicationRequest == null ) {
            return null;
        }

        EventApplication.EventApplicationBuilder eventApplication = EventApplication.builder();

        eventApplication.foodTruckId( eventApplicationRequest.foodTruckId() );
        eventApplication.comment( eventApplicationRequest.comment() );

        return eventApplication.build();
    }
}
