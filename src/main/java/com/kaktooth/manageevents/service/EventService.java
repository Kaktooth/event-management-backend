package com.kaktooth.manageevents.service;

import com.kaktooth.manageevents.model.dto.EventDto;
import com.kaktooth.manageevents.model.mapper.EventMapper;
import com.kaktooth.manageevents.persistance.entity.Event;
import com.kaktooth.manageevents.persistance.entity.User;
import com.kaktooth.manageevents.persistance.repository.ChatRepository;
import com.kaktooth.manageevents.persistance.repository.EventRepository;
import com.kaktooth.manageevents.persistance.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class EventService {

  private final EventRepository eventRepository;
  private final UserRepository userRepository;
  private final ChatRepository chatRepository;

  public List<EventDto> getAllApprovedEvents() {
    var username = SecurityContextHolder.getContext().getAuthentication().getName();
    var user = userRepository.findUserByUsername(username);

    var visibleEvents = new ArrayList<Event>();
    var approvedEvents = eventRepository.findEventsByApprovedIsTrue();

    for (var event : approvedEvents) {
      switch (event.getVisibility()) {
        case PUBLIC -> visibleEvents.add(event);
        case FRIENDS_ONLY -> {
          if (event.getCreator().getFriends().contains(user)) {
            visibleEvents.add(event);
          }
        }
        case SELECTED_INDIVIDUALS -> {
          if (event.getGuests().contains(user)) {
            visibleEvents.add(event);
          }
        }
        case SELECTED_CHAT_INDIVIDUALS -> {
          if (event.getGuests().contains(user) && event.getChat().getGuests().contains(user)) {
            visibleEvents.add(event);
          }
        }
      }
    }
    return EventMapper.INSTANCE.toDto(visibleEvents);
  }

  @PreAuthorize("hasAuthority('MODERATOR')")
  public List<EventDto> getEventsToModerate() {
    var events = eventRepository.findEventsByApprovedIsFalse();
    return EventMapper.INSTANCE.toDto(events);
  }

  public List<EventDto> getByCreatorId(UUID userId) {
    var creatorEvents = eventRepository.findAllByCreatorId(userId);
    return EventMapper.INSTANCE.toDto(creatorEvents);
  }

  public EventDto save(EventDto eventDto) {
    var username = SecurityContextHolder.getContext().getAuthentication().getName();
    var user = userRepository.findUserByUsername(username);
    var chat = chatRepository.findById(eventDto.getChat().getId());
    var guestsIds = eventDto.getGuests().stream().map(User::getId).toList();
    var guests = (List<User>) userRepository.findAllById(guestsIds);
    var event = EventMapper.INSTANCE.toEntity(eventDto);

    if (eventDto.getId() != null) {
      var eventEntity = eventRepository.findById(eventDto.getId());
      eventEntity.ifPresent(value -> event.setCreatedDate(value.getCreatedDate()));
    }

    if (chat.isEmpty()) {
      throw new NoSuchElementException(
          "Cant create event. No such chat by id: " + eventDto.getChat().getId());
    }

    event.setCreator(user);
    event.setGuests(guests);
    event.setChat(chat.get());
    event.setApproved(false);
    var savedEvent = eventRepository.save(event);
    return EventMapper.INSTANCE.toDto(savedEvent);
  }

  public EventDto update(EventDto eventDto, UUID eventId) {
    var username = SecurityContextHolder.getContext().getAuthentication().getName();
    var userId = userRepository.findUserByUsername(username).getId();
    if (eventRepository.findEventByIdAndCreatorId(eventId, userId).isEmpty()) {
      throw new AuthorizationServiceException("Cant authorize incorrect creator");
    }

    return save(eventDto);
  }

  public void delete(UUID eventId) {
    var username = SecurityContextHolder.getContext().getAuthentication().getName();
    var userId = userRepository.findUserByUsername(username).getId();
    if (eventRepository.findEventByIdAndCreatorId(eventId, userId).isEmpty()) {
      throw new AuthorizationServiceException("Cant authorize incorrect creator");
    }
    eventRepository.deleteById(eventId);
  }

  @PreAuthorize("hasAuthority('MODERATOR')")
  public void approve(UUID eventId) {
    var eventRecord = eventRepository.findById(eventId);

    if (eventRecord.isPresent()) {
      var event = eventRecord.get();
      event.setApproved(true);
      eventRepository.save(event);
    }
  }
}
