package com.nulp.mobilepartsshop.core.service.adminPanel;

import com.nulp.mobilepartsshop.api.v1.adminPanel.dto.request.UserRegistrationRequest;
import com.nulp.mobilepartsshop.api.v1.adminPanel.dto.response.UserRegistrationResponse;
import com.nulp.mobilepartsshop.api.v1.adminPanel.service.UserRegistrationService;
import com.nulp.mobilepartsshop.core.entity.user.User;
import com.nulp.mobilepartsshop.core.enums.UserAuthority;
import com.nulp.mobilepartsshop.core.repository.user.UserRepository;
import com.nulp.mobilepartsshop.exception.adminPanel.AdminAlreadyExistsException;
import com.nulp.mobilepartsshop.exception.authentication.UsernameAlreadyUsedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService {

    @Value("${admin.max-count}")
    private int MAX_ADMINS_COUNT;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserRegistrationResponse registerCustomer(UserRegistrationRequest request) throws UsernameAlreadyUsedException {
        return register(request, UserAuthority.CUSTOMER);
    }

    @Override
    public UserRegistrationResponse registerStaff(UserRegistrationRequest request) throws UsernameAlreadyUsedException {
        return register(request, UserAuthority.STAFF);
    }

    @Override
    public UserRegistrationResponse registerAdmin(UserRegistrationRequest request) throws UsernameAlreadyUsedException, AdminAlreadyExistsException {
        List<User> admins = userRepository.findByAuthority(UserAuthority.ADMIN);
        if (admins.size() >= MAX_ADMINS_COUNT) {
            throw new AdminAlreadyExistsException();
        }
        return register(request, UserAuthority.ADMIN);
    }

    private UserRegistrationResponse register(UserRegistrationRequest request, UserAuthority authority) throws UsernameAlreadyUsedException {
        final Optional<User> user = userRepository.findByUsername(request.getUsername());
        if (user.isPresent()) {
            throw new UsernameAlreadyUsedException("Username is already used");
        }
        final User newUser = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .authority(authority)
                .build();
        final User registeredUser = userRepository.save(newUser);
        return UserRegistrationResponse.builder()
                .userId(registeredUser.getId())
                .build();
    }
}
