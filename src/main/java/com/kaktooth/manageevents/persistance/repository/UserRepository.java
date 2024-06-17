package com.kaktooth.manageevents.persistance.repository;

import com.kaktooth.manageevents.persistance.entity.User;
import java.util.UUID;

public interface UserRepository extends CommonRepository<User, UUID> {

  User findUserByUsername(String username);
}
