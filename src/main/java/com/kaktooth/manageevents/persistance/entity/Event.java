package com.kaktooth.manageevents.persistance.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Data
public class Event {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  private String name;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdDate;

  @Enumerated(EnumType.ORDINAL)
  private EventVisibility visibility;

  private boolean approved;

  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE,
      CascadeType.PERSIST, CascadeType.REFRESH})
  private User creator;

  @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE,
      CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinTable(name = "event_guest",
      joinColumns = {@JoinColumn(name = "event_id")},
      inverseJoinColumns = {@JoinColumn(name = "guest_id")})
  private List<User> guests;

  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE,
      CascadeType.PERSIST, CascadeType.REFRESH})
  private Chat chat;
}
