package com.nulp.mobilepartsshop.domain.service;

import com.nulp.mobilepartsshop.domain.model.user.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    User loadUserByUsername(String username) throws UsernameNotFoundException;
}
