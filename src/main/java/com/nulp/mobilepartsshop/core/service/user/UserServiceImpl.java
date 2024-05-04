package com.nulp.mobilepartsshop.core.service.user;

import com.nulp.mobilepartsshop.api.v1.user.service.UserService;
import com.nulp.mobilepartsshop.core.entity.user.User;
import com.nulp.mobilepartsshop.core.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUser(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> updateUser() {
        return Optional.empty();
    }
}
