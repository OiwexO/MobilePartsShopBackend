package com.nulp.mobilepartsshop.api.v1.authentication.controller;

import com.nulp.mobilepartsshop.api.v1.ApiConstants;
import com.nulp.mobilepartsshop.api.v1.authentication.dto.request.AuthorizationRequest;
import com.nulp.mobilepartsshop.api.v1.authentication.dto.request.RegistrationRequest;
import com.nulp.mobilepartsshop.api.v1.authentication.dto.response.AuthorizationResponse;
import com.nulp.mobilepartsshop.api.v1.authentication.service.AuthenticationService;
import com.nulp.mobilepartsshop.exception.authentication.InvalidPasswordException;
import com.nulp.mobilepartsshop.exception.authentication.UsernameAlreadyUsedException;
import com.nulp.mobilepartsshop.exception.authentication.UsernameNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AuthenticationController.MAPPING)
@RequiredArgsConstructor
public class AuthenticationController {

    public static final String MAPPING = ApiConstants.GLOBAL_MAPPING_V1 + "/authentication";

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthorizationResponse> register(@RequestBody RegistrationRequest request) {
        final AuthorizationResponse response;
        try {
            response = authenticationService.register(request);
            return ResponseEntity.ok(response);
        } catch (UsernameAlreadyUsedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
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
        final AuthorizationResponse response;
        try {
            if (isCustomer) {
                response = authenticationService.authorizeCustomer(request);
            } else {
                response = authenticationService.authorizeStaffOrAdmin(request);
            }
            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (InvalidPasswordException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
