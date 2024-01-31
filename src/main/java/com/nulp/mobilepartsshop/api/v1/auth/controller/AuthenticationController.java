package com.nulp.mobilepartsshop.api.v1.auth.controller;

import com.nulp.mobilepartsshop.api.v1.auth.dto.request.AuthenticationRequest;
import com.nulp.mobilepartsshop.api.v1.auth.dto.request.RegistrationRequest;
import com.nulp.mobilepartsshop.api.v1.auth.dto.response.AuthenticationResponse;
import com.nulp.mobilepartsshop.api.v1.auth.service.AuthenticationService;
import com.nulp.mobilepartsshop.exception.auth.InvalidPasswordException;
import com.nulp.mobilepartsshop.exception.auth.UsernameAlreadyUsedException;
import com.nulp.mobilepartsshop.exception.auth.UsernameNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AuthenticationController.AUTHENTICATION_MAPPING)
@RequiredArgsConstructor
public class AuthenticationController {
    public static final String AUTHENTICATION_MAPPING = "/api/v1/auth";

    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegistrationRequest request
    ) {
        AuthenticationResponse response;
        try {
            response = authenticationService.register(request);
            return ResponseEntity.ok(response);
        } catch (UsernameAlreadyUsedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new AuthenticationResponse());
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        AuthenticationResponse response;
        try {
            response = authenticationService.authenticate(request);
            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AuthenticationResponse());
        } catch (InvalidPasswordException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthenticationResponse());
        }
    }
}
