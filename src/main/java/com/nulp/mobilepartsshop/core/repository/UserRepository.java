package com.nulp.mobilepartsshop.core.repository;

import com.nulp.mobilepartsshop.core.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
