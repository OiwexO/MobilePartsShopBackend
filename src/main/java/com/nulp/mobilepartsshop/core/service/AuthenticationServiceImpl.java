package com.nulp.mobilepartsshop.core.service;

import com.nulp.mobilepartsshop.api.v1.auth.dto.request.AuthenticationRequest;
import com.nulp.mobilepartsshop.api.v1.auth.dto.request.RegistrationRequest;
import com.nulp.mobilepartsshop.api.v1.auth.dto.response.AuthenticationResponse;
import com.nulp.mobilepartsshop.api.v1.auth.service.AuthenticationService;
import com.nulp.mobilepartsshop.core.enums.UserRole;
import com.nulp.mobilepartsshop.core.model.user.User;
import com.nulp.mobilepartsshop.core.repository.UserRepository;
import com.nulp.mobilepartsshop.exception.auth.InvalidPasswordException;
import com.nulp.mobilepartsshop.exception.auth.UsernameAlreadyUsedException;
import com.nulp.mobilepartsshop.exception.auth.UsernameNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegistrationRequest request) throws UsernameAlreadyUsedException {
        Optional<User> user = repository.findByUsername(request.getUsername());
        if (user.isPresent()) {
            throw new UsernameAlreadyUsedException("Username is already used");
        }
        final User newUser = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .role(UserRole.CUSTOMER)
                .build();
        repository.save(newUser);
        final String jwtToken = jwtService.generateToken(newUser);
        return AuthenticationResponse.builder()
                .jwtToken(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request)
            throws UsernameNotFoundException, InvalidPasswordException {
        final User user = repository.findByUsername(request.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("Username not found")
        );
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );
        try {
            authenticationManager.authenticate(authToken);
        } catch (AuthenticationException e) {
            throw new InvalidPasswordException("Password is invalid");
        }
        final String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .jwtToken(jwtToken)
                .build();
    }

}
