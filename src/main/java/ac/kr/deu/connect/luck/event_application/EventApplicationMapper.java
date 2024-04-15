package ac.kr.deu.connect.luck.event_application;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventApplicationMapper {

    @Mapping(source = "comment", target = "comment")
    EventApplication toEventApplication(EventApplicationRequest eventApplicationRequest);
}
