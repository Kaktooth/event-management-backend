package com.kaktooth.manageevents.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class EndpointsConfiguration {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity,
                                         AuthenticationProvider authenticationProvider)
      throws Exception {

    httpSecurity
        .csrf(AbstractHttpConfigurer::disable)
        .authenticationProvider(authenticationProvider)
        .httpBasic(Customizer.withDefaults())
        .authorizeHttpRequests(
            req -> req.requestMatchers("/event/*/approve", "/moderate-events")
                .hasAuthority("MODERATOR"))
        .authorizeHttpRequests(req -> req.anyRequest().authenticated())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    return httpSecurity.build();
  }
}
