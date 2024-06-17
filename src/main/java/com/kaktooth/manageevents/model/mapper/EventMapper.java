package com.kaktooth.manageevents.model.mapper;

import com.kaktooth.manageevents.model.dto.EventDto;
import com.kaktooth.manageevents.persistance.entity.Event;
import jakarta.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EventMapper {

  EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

  default List<EventDto> toDto(@Nonnull Iterable<Event> events) {
    List<EventDto> eventsDto = new ArrayList<>();
    for (var event : events) {
      eventsDto.add(toDto(event));
    }
    return eventsDto;
  }

  default List<Event> toEntity(@Nonnull Iterable<EventDto> eventsDto) {
    List<Event> events = new ArrayList<>();
    for (var eventDto : eventsDto) {
      events.add(toEntity(eventDto));
    }
    return events;
  }

  @Mapping(target = "name")
  Event toEntity(@Nonnull EventDto eventDto);

  @Mapping(target = "name")
  EventDto toDto(@Nonnull Event eventEntity);
}
