package com.nulp.mobilepartsshop.core.service.adminPanel;

import com.nulp.mobilepartsshop.api.v1.adminPanel.dto.request.UserRegistrationRequest;
import com.nulp.mobilepartsshop.api.v1.adminPanel.dto.response.UserRegistrationResponse;
import com.nulp.mobilepartsshop.api.v1.adminPanel.service.UserRegistrationService;
import com.nulp.mobilepartsshop.core.entity.user.User;
import com.nulp.mobilepartsshop.core.repository.UserRepository;
import com.nulp.mobilepartsshop.exception.authentication.UsernameAlreadyUsedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserRegistrationResponse register(UserRegistrationRequest request) throws UsernameAlreadyUsedException {
        final Optional<User> user = repository.findByUsername(request.getUsername());
        if (user.isPresent()) {
            throw new UsernameAlreadyUsedException("Username is already used");
        }
        final User newUser = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .authority(request.getAuthority())
                .build();
        User registeredUser = repository.save(newUser);
//        final Optional<User> registeredUser = repository.findByUsername(request.getUsername());
        return UserRegistrationResponse.builder()
                .userId(registeredUser.getId())
                .build();
    }
}
