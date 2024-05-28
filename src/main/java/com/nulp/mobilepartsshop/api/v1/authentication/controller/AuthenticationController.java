package com.nulp.mobilepartsshop.api.v1.authentication.controller;

import com.nulp.mobilepartsshop.api.v1.ApiConstants;
import com.nulp.mobilepartsshop.api.v1.authentication.dto.request.AuthorizationRequest;
import com.nulp.mobilepartsshop.api.v1.authentication.dto.request.RegistrationRequest;
import com.nulp.mobilepartsshop.api.v1.authentication.dto.response.AuthorizationResponse;
import com.nulp.mobilepartsshop.api.v1.authentication.dto.response.EncryptionDataResponse;
import com.nulp.mobilepartsshop.api.v1.authentication.service.AuthenticationService;
import com.nulp.mobilepartsshop.api.v1.user.dto.mapper.UserMapper;
import com.nulp.mobilepartsshop.api.v1.user.dto.response.UserResponse;
import com.nulp.mobilepartsshop.core.entity.authentication.AuthorizationData;
import com.nulp.mobilepartsshop.core.entity.authentication.AuthorizationResponseData;
import com.nulp.mobilepartsshop.core.entity.authentication.RegistrationData;
import com.nulp.mobilepartsshop.core.entity.security.EncryptionData;
import com.nulp.mobilepartsshop.exception.authentication.InvalidPasswordException;
import com.nulp.mobilepartsshop.exception.authentication.UsernameAlreadyUsedException;
import com.nulp.mobilepartsshop.exception.authentication.UsernameNotFoundException;
import com.nulp.mobilepartsshop.exception.security.DecryptionException;
import com.nulp.mobilepartsshop.security.service.DecryptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AuthenticationController.MAPPING)
@RequiredArgsConstructor
public class AuthenticationController {

    public static final String MAPPING = ApiConstants.GLOBAL_MAPPING_V1 + "/authentication";

    private final AuthenticationService authenticationService;

    private final DecryptionService decryptionService;

    private final UserMapper mapper = new UserMapper();

    @GetMapping("/encryption-data")
    public ResponseEntity<EncryptionDataResponse> getEncryptionData() {
        final EncryptionData data = decryptionService.getEncryptionData();
        final EncryptionDataResponse response = EncryptionDataResponse.builder()
                .cipher(data.cipher())
                .keyAlgorithm(data.keyAlgorithm())
                .publicKeyBase64(data.publicKeyBase64())
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthorizationResponse> register(@RequestBody RegistrationRequest request) {
        try {
            final String decryptedPassword = decryptionService.decrypt(request.getPassword());
            RegistrationData data = RegistrationData.builder()
                    .username(request.getUsername())
                    .password(decryptedPassword)
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .build();
            final AuthorizationResponseData responseData = authenticationService.register(data);
            final UserResponse userResponse = mapper.toResponse(responseData.user());
            final AuthorizationResponse response = AuthorizationResponse.builder()
                    .user(userResponse)
                    .jwtToken(responseData.jwtToken())
                    .build();
            return ResponseEntity.ok(response);
        } catch (UsernameAlreadyUsedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (DecryptionException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/authorize/staff-admin")
    public ResponseEntity<AuthorizationResponse> authorizeStaffOrAdmin(@RequestBody AuthorizationRequest request) {
        return authorize(request, false);
    }

    @PostMapping("/authorize/customer")
    public ResponseEntity<AuthorizationResponse> authorizeCustomer(@RequestBody AuthorizationRequest request) {
        return authorize(request, true);
    }

    private ResponseEntity<AuthorizationResponse> authorize(AuthorizationRequest request, boolean isCustomer) {
        try {
            final String decryptedPassword = decryptionService.decrypt(request.getPassword());
            AuthorizationData data = AuthorizationData.builder()
                    .username(request.getUsername())
                    .password(decryptedPassword)
                    .build();
            final AuthorizationResponseData responseData;
            if (isCustomer) {
                responseData = authenticationService.authorizeCustomer(data);
            } else {
                responseData = authenticationService.authorizeStaffOrAdmin(data);
            }
            final UserResponse userResponse = mapper.toResponse(responseData.user());
            final AuthorizationResponse response = AuthorizationResponse.builder()
                    .user(userResponse)
                    .jwtToken(responseData.jwtToken())
                    .build();
            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (InvalidPasswordException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (DecryptionException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
