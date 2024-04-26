package com.nulp.mobilepartsshop.core.repository.user;

import com.nulp.mobilepartsshop.core.entity.user.User;
import com.nulp.mobilepartsshop.core.enums.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByAuthority(UserAuthority authority);

    Optional<User> findByUsername(String username);
}
