package com.kaktooth.manageevents.persistance.repository;

import com.kaktooth.manageevents.persistance.entity.Event;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventRepository extends CommonRepository<Event, UUID> {

  List<Event> findEventsByApprovedIsTrue();

  List<Event> findEventsByApprovedIsFalse();

  Optional<Event> findEventByIdAndCreatorId(UUID eventId, UUID creatorId);

  List<Event> findAllByCreatorId(UUID creatorId);
}
