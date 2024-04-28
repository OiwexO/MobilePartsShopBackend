package com.nulp.mobilepartsshop.core.service.authentication;

import com.nulp.mobilepartsshop.api.v1.authentication.dto.request.AuthorizationRequest;
import com.nulp.mobilepartsshop.api.v1.authentication.dto.request.RegistrationRequest;
import com.nulp.mobilepartsshop.api.v1.authentication.dto.response.AuthorizationResponse;
import com.nulp.mobilepartsshop.api.v1.authentication.service.AuthenticationService;
import com.nulp.mobilepartsshop.security.service.JwtService;
import com.nulp.mobilepartsshop.core.enums.UserAuthority;
import com.nulp.mobilepartsshop.core.entity.user.User;
import com.nulp.mobilepartsshop.core.repository.user.UserRepository;
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

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Override
    public AuthorizationResponse register(RegistrationRequest request) throws UsernameAlreadyUsedException {
        final Optional<User> user = userRepository.findByUsername(request.getUsername());
        if (user.isPresent()) {
            throw new UsernameAlreadyUsedException();
        }
        final User newUser = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .authority(UserAuthority.CUSTOMER)
                .build();
        User savedUser = userRepository.save(newUser);
        final String jwtToken = jwtService.generateToken(newUser);
        return AuthorizationResponse.builder()
                .userId(savedUser.getId())
                .jwtToken(jwtToken)
                .build();
    }

    @Override
    public AuthorizationResponse authorize(
            AuthorizationRequest request
    ) throws UsernameNotFoundException, InvalidPasswordException {
        final User user = userRepository.findByUsername(request.getUsername()).orElseThrow(
                UsernameNotFoundException::new
        );
        final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );
        try {
            authenticationManager.authenticate(authToken);
        } catch (AuthenticationException e) {
            throw new InvalidPasswordException();
        }
        final String jwtToken = jwtService.generateToken(user);
        return AuthorizationResponse.builder()
                .userId(user.getId())
                .jwtToken(jwtToken)
                .build();
    }
}
