package com.kaktooth.manageevents.model.mapper;

import com.kaktooth.manageevents.model.dto.UserDto;
import com.kaktooth.manageevents.persistance.entity.User;
import jakarta.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  default List<UserDto> toDto(@Nonnull Iterable<User> users) {
    List<UserDto> usersDto = new ArrayList<>();
    for (var user : users) {
      usersDto.add(toDto(user));
    }
    return usersDto;
  }

  default List<User> toEntity(@Nonnull Iterable<UserDto> usersDto) {
    List<User> users = new ArrayList<>();
    for (var userDto : usersDto) {
      users.add(toEntity(userDto));
    }
    return users;
  }

  @Mapping(target = "username")
  User toEntity(@Nonnull UserDto userDto);

  @Mapping(target = "username")
  UserDto toDto(@Nonnull User userEntity);
}
