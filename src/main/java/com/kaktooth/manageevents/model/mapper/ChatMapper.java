package com.kaktooth.manageevents.model.mapper;

import com.kaktooth.manageevents.model.dto.ChatDto;
import com.kaktooth.manageevents.persistance.entity.Chat;
import jakarta.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ChatMapper {

  ChatMapper INSTANCE = Mappers.getMapper(ChatMapper.class);

  default List<ChatDto> toDto(@Nonnull Iterable<Chat> chats) {
    List<ChatDto> chatsDto = new ArrayList<>();
    for (var chat : chats) {
      chatsDto.add(toDto(chat));
    }
    return chatsDto;
  }

  default List<Chat> toEntity(@Nonnull Iterable<ChatDto> chatsDto) {
    List<Chat> chats = new ArrayList<>();
    for (var chatDto : chatsDto) {
      chats.add(toEntity(chatDto));
    }
    return chats;
  }

  @Mapping(target = "name")
  Chat toEntity(@Nonnull ChatDto chatDto);

  @Mapping(target = "name")
  ChatDto toDto(@Nonnull Chat chatEntity);
}
