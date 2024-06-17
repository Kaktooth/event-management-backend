package com.kaktooth.manageevents.persistance.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Chat {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  private String name;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdDate;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private User creator;

  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinTable(name = "chat_guest",
      joinColumns = {@JoinColumn(name = "chat_id")},
      inverseJoinColumns = {@JoinColumn(name = "guest_id")})
  private List<User> guests;
}
