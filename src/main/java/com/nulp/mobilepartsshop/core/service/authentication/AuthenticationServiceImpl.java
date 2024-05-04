package com.nulp.mobilepartsshop.core.service.authentication;

import com.nulp.mobilepartsshop.api.v1.authentication.dto.request.AuthorizationRequest;
import com.nulp.mobilepartsshop.api.v1.authentication.dto.request.RegistrationRequest;
import com.nulp.mobilepartsshop.api.v1.authentication.dto.response.AuthorizationResponse;
import com.nulp.mobilepartsshop.api.v1.authentication.service.AuthenticationService;
import com.nulp.mobilepartsshop.api.v1.user.dto.response.UserResponse;
import com.nulp.mobilepartsshop.core.service.email.EmailService;
import com.nulp.mobilepartsshop.security.service.JwtService;
import com.nulp.mobilepartsshop.core.enums.user.UserAuthority;
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

    private final EmailService emailService;

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
        emailService.sendGreetingCustomerEmail(savedUser.getUsername(), savedUser.getFirstname());
        return buildResponse(savedUser, jwtToken);
    }

    @Override
    public AuthorizationResponse authorizeStaffOrAdmin(
            AuthorizationRequest request
    ) throws UsernameNotFoundException, InvalidPasswordException {
        return authorize(request, false);
    }

    @Override
    public AuthorizationResponse authorizeCustomer(
            AuthorizationRequest request
    ) throws UsernameNotFoundException, InvalidPasswordException {
        return authorize(request, true);
    }

    private AuthorizationResponse authorize(
            AuthorizationRequest request, boolean isCustomer
    ) throws UsernameNotFoundException, InvalidPasswordException {
        final User user = userRepository.findByUsername(request.getUsername()).orElseThrow(
                UsernameNotFoundException::new
        );
        if (isCustomer != (user.getAuthority() == UserAuthority.CUSTOMER)) {
            throw new UsernameNotFoundException();
        }
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
        return buildResponse(user, jwtToken);
    }

    private AuthorizationResponse buildResponse(User user, String jwtToken) {
        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .authority(user.getAuthority())
                .build();
        return AuthorizationResponse.builder()
                .user(userResponse)
                .jwtToken(jwtToken)
                .build();
    }
}
