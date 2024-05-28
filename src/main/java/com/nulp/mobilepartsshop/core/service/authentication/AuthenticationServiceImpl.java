package com.nulp.mobilepartsshop.core.service.authentication;

import com.nulp.mobilepartsshop.api.v1.authentication.service.AuthenticationService;
import com.nulp.mobilepartsshop.core.entity.authentication.AuthorizationData;
import com.nulp.mobilepartsshop.core.entity.authentication.AuthorizationResponseData;
import com.nulp.mobilepartsshop.core.entity.authentication.RegistrationData;
import com.nulp.mobilepartsshop.core.entity.user.User;
import com.nulp.mobilepartsshop.core.enums.user.UserAuthority;
import com.nulp.mobilepartsshop.core.repository.user.UserRepository;
import com.nulp.mobilepartsshop.core.service.email.EmailService;
import com.nulp.mobilepartsshop.exception.authentication.InvalidPasswordException;
import com.nulp.mobilepartsshop.exception.authentication.UsernameAlreadyUsedException;
import com.nulp.mobilepartsshop.exception.authentication.UsernameNotFoundException;
import com.nulp.mobilepartsshop.security.service.JwtService;
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
    public AuthorizationResponseData register(RegistrationData data) throws UsernameAlreadyUsedException {
        final Optional<User> user = userRepository.findByUsername(data.username());
        if (user.isPresent()) {
            throw new UsernameAlreadyUsedException();
        }
        final User newUser = User.builder()
                .username(data.username())
                .password(passwordEncoder.encode(data.password()))
                .firstname(data.firstname())
                .lastname(data.lastname())
                .authority(UserAuthority.CUSTOMER)
                .build();
        User savedUser = userRepository.save(newUser);
        final String jwtToken = jwtService.generateToken(newUser);
        emailService.sendGreetingCustomerEmail(savedUser.getUsername(), savedUser.getFirstname());
        return AuthorizationResponseData.builder()
                .user(savedUser)
                .jwtToken(jwtToken)
                .build();
    }

    @Override
    public AuthorizationResponseData authorizeStaffOrAdmin(
            AuthorizationData data
    ) throws UsernameNotFoundException, InvalidPasswordException {
        return authorize(data, false);
    }

    @Override
    public AuthorizationResponseData authorizeCustomer(
            AuthorizationData data
    ) throws UsernameNotFoundException, InvalidPasswordException {
        return authorize(data, true);
    }

    private AuthorizationResponseData authorize(
            AuthorizationData data, boolean isCustomer
    ) throws UsernameNotFoundException, InvalidPasswordException {
        final User user = userRepository.findByUsername(data.username()).orElseThrow(
                UsernameNotFoundException::new
        );
        if (isCustomer != (user.getAuthority() == UserAuthority.CUSTOMER)) {
            throw new UsernameNotFoundException();
        }
        final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                data.username(),
                data.password()
        );
        try {
            authenticationManager.authenticate(authToken);
        } catch (AuthenticationException e) {
            throw new InvalidPasswordException();
        }
        final String jwtToken = jwtService.generateToken(user);
        return AuthorizationResponseData.builder()
                .user(user)
                .jwtToken(jwtToken)
                .build();
    }
}
