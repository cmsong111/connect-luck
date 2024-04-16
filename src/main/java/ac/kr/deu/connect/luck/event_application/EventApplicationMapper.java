package ac.kr.deu.connect.luck.event_application;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventApplicationMapper {


    EventApplication toEventApplication(EventApplicationRequest eventApplicationRequest);
}
