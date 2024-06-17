package com.kaktooth.manageevents.model.dto;

import com.kaktooth.manageevents.persistance.entity.User;
import java.util.List;
import java.util.UUID;
import lombok.Value;

@Value
public class ChatDto {

  UUID id;

  String name;

  User creator;

  List<User> guests;
}
