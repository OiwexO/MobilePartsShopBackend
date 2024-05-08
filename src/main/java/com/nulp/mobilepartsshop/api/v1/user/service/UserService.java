package com.nulp.mobilepartsshop.api.v1.user.service;

import com.nulp.mobilepartsshop.core.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();

    Optional<User> getUser(Long userId);

    Optional<User> updateUser();
}
