package ac.kr.deu.connect.luck.eventApplication;

import ac.kr.deu.connect.luck.Application;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventApplicationMapper {

    @Mapping(source = "comment", target = "comment")
    EventApplication toEventApplication(EventApplicationRequest eventApplicationRequest);
}
