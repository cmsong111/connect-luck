package ac.kr.deu.connect.luck.event;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventMapper {
    @Mapping(target = "status", defaultValue = "BEFORE_APPLICATION", ignore = true)
    @Mapping(source = "managerId", target = "manager.id")
    Event toEvent(EventRequest eventRequest);
}
