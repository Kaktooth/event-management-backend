package com.kaktooth.manageevents.model.dto;

import com.kaktooth.manageevents.persistance.entity.User;
import com.kaktooth.manageevents.persistance.entity.UserAuthority;
import java.util.List;
import java.util.UUID;
import lombok.Value;

@Value
public class UserDto {

  UUID id;

  String username;

  UserAuthority authority;

  List<User> friends;
}
