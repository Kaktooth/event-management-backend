package com.kaktooth.manageevents.model.dto;

import com.kaktooth.manageevents.persistance.entity.Chat;
import com.kaktooth.manageevents.persistance.entity.EventVisibility;
import com.kaktooth.manageevents.persistance.entity.User;
import java.util.List;
import java.util.UUID;
import lombok.Value;

@Value
public class EventDto {

  UUID id;

  String name;

  EventVisibility visibility;

  User creator;

  List<User> guests;

  Chat chat;
}
