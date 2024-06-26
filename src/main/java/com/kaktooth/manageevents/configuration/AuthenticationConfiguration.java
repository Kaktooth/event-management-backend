package com.kaktooth.manageevents.configuration;

import com.kaktooth.manageevents.persistance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AuthenticationConfiguration {

  private final UserRepository userRepository;

  @Bean
  UserDetailsService userDetailsService() {
    return (username) -> {
      final var user = userRepository.findUserByUsername(username);
      if (user == null) {
        throw new UsernameNotFoundException(username + " username is not found!");
      }

      return user;
    };
  }

  @Bean
  public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,
                                                       PasswordEncoder passwordEncoder) {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder);
    return authenticationProvider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
