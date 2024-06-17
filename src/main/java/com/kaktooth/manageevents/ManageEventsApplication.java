package com.kaktooth.manageevents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class ManageEventsApplication {

  public static void main(String[] args) {
    SpringApplication.run(ManageEventsApplication.class, args);
  }

}
