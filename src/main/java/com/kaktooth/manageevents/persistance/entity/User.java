package com.kaktooth.manageevents.persistance.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
@SecondaryTable(name = "authorities", pkJoinColumns = {@PrimaryKeyJoinColumn(name = "user_id")})
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  private String username;

  private String password;

  @Column(table = "authorities", name = "authority")
  @Enumerated(EnumType.ORDINAL)
  private UserAuthority authority;


  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinTable(name = "user_friend",
      joinColumns = {@JoinColumn(name = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "friend_id")})
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
  private List<User> friends;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    var userAuthority = new SimpleGrantedAuthority(authority.name());
    return Collections.singletonList(userAuthority);
  }
}
