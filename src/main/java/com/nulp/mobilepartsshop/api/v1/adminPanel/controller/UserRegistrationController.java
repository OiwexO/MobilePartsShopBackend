package com.nulp.mobilepartsshop.api.v1.adminPanel.controller;

import com.nulp.mobilepartsshop.api.v1.ApiConstants;
import com.nulp.mobilepartsshop.api.v1.adminPanel.dto.request.UserRegistrationRequest;
import com.nulp.mobilepartsshop.api.v1.adminPanel.dto.response.UserRegistrationResponse;
import com.nulp.mobilepartsshop.api.v1.adminPanel.service.UserRegistrationService;
import com.nulp.mobilepartsshop.exception.authentication.UsernameAlreadyUsedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UserRegistrationController.MAPPING)
@RequiredArgsConstructor
public class UserRegistrationController {
    public static final String MAPPING = ApiConstants.ADMIN_MAPPING + "/userRegistration";

    private final UserRegistrationService userRegistrationService;

    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponse> register(
            @RequestBody UserRegistrationRequest request
    ) {
        UserRegistrationResponse response;
        try {
            response = userRegistrationService.register(request);
            return ResponseEntity.ok(response);
        } catch (UsernameAlreadyUsedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new UserRegistrationResponse());
        }
    }
}
