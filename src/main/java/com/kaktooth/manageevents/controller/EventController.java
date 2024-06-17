package com.kaktooth.manageevents.controller;

import com.kaktooth.manageevents.model.dto.EventDto;
import com.kaktooth.manageevents.service.EventService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EventController {

  private final EventService eventService;

  @GetMapping("/events")
  public ResponseEntity<List<EventDto>> getEvents() {
    var approvedEvents = eventService.getAllApprovedEvents();
    return ResponseEntity.ok(approvedEvents);
  }

  @GetMapping("/moderate-events")
  public ResponseEntity<List<EventDto>> getEventsToModerate() {
    var moderateEvents = eventService.getEventsToModerate();
    return ResponseEntity.ok(moderateEvents);
  }

  @GetMapping("/user-events/{id}")
  public ResponseEntity<List<EventDto>> getUserEvents(@PathVariable UUID id) {
    var creatorEvents = eventService.getByCreatorId(id);
    return ResponseEntity.ok(creatorEvents);
  }

  @PutMapping("/event/{id}/approve")
  public ResponseEntity<String> approveEvent(@PathVariable UUID id) {
    eventService.approve(id);
    return ResponseEntity.ok("Event " + id + "is approved");
  }

  @PutMapping("/event/{id}/update")
  public ResponseEntity<EventDto> updateEvent(@PathVariable UUID id,
                                              @RequestBody EventDto eventDto) {
    var updatedEvent = eventService.update(eventDto, id);
    return ResponseEntity.ok(updatedEvent);
  }

  @DeleteMapping("/event/{id}/delete")
  public ResponseEntity<String> updateEvent(@PathVariable UUID id) {
    eventService.delete(id);
    return ResponseEntity.ok("Event is successfully deleted");
  }

  @PostMapping("/save-event")
  public ResponseEntity<EventDto> saveEvent(@RequestBody EventDto eventDto) {
    var savedEvent = eventService.save(eventDto);
    return ResponseEntity.ok(savedEvent);
  }
}
