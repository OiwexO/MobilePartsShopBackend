package com.nulp.mobilepartsshop.core.service;

import com.nulp.mobilepartsshop.api.v1.authentication.dto.request.AuthorizationRequest;
import com.nulp.mobilepartsshop.api.v1.authentication.dto.request.RegistrationRequest;
import com.nulp.mobilepartsshop.api.v1.authentication.dto.response.AuthorizationResponse;
import com.nulp.mobilepartsshop.api.v1.authentication.service.AuthenticationService;
import com.nulp.mobilepartsshop.security.service.JwtService;
import com.nulp.mobilepartsshop.core.enums.UserAuthority;
import com.nulp.mobilepartsshop.core.model.user.User;
import com.nulp.mobilepartsshop.core.repository.UserRepository;
import com.nulp.mobilepartsshop.exception.authentication.InvalidPasswordException;
import com.nulp.mobilepartsshop.exception.authentication.UsernameAlreadyUsedException;
import com.nulp.mobilepartsshop.exception.authentication.UsernameNotFoundException;
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
    public AuthorizationResponse register(RegistrationRequest request) throws UsernameAlreadyUsedException {
        final Optional<User> user = repository.findByUsername(request.getUsername());
        if (user.isPresent()) {
            throw new UsernameAlreadyUsedException("Username is already used");
        }
        final User newUser = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .role(UserAuthority.CUSTOMER)
                .build();
        repository.save(newUser);
        final String jwtToken = jwtService.generateToken(newUser);
        return AuthorizationResponse.builder()
                .jwtToken(jwtToken)
                .build();
    }

    @Override
    public AuthorizationResponse authorize(
            AuthorizationRequest request
    ) throws UsernameNotFoundException, InvalidPasswordException {
        final User user = repository.findByUsername(request.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("Username not found")
        );
        final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );
        try {
            authenticationManager.authenticate(authToken);
        } catch (AuthenticationException e) {
            throw new InvalidPasswordException("Invalid password");
        }
        final String jwtToken = jwtService.generateToken(user);
        return AuthorizationResponse.builder()
                .jwtToken(jwtToken)
                .build();
    }

}
