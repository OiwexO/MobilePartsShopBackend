package com.nulp.mobilepartsshop.core.service;

import com.nulp.mobilepartsshop.core.model.user.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    User loadUserByUsername(String username) throws UsernameNotFoundException;
}
