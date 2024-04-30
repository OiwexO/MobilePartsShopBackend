package com.nulp.mobilepartsshop.api.v1.adminPanel.controller;

import com.nulp.mobilepartsshop.api.v1.ApiConstants;
import com.nulp.mobilepartsshop.api.v1.adminPanel.dto.request.UserRegistrationRequest;
import com.nulp.mobilepartsshop.api.v1.adminPanel.dto.response.UserRegistrationResponse;
import com.nulp.mobilepartsshop.api.v1.adminPanel.service.UserRegistrationService;
import com.nulp.mobilepartsshop.exception.adminPanel.AdminAlreadyExistsException;
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

    public static final String MAPPING = ApiConstants.ADMIN_MAPPING_V1 + "/register";

    public static final String ADMIN_REGISTRATION_MAPPING = MAPPING + "/admin";

    private final UserRegistrationService userRegistrationService;

    @PostMapping("/customer")
    public ResponseEntity<UserRegistrationResponse> registerCustomer(@RequestBody UserRegistrationRequest request) {
        final UserRegistrationResponse response;
        try {
            response = userRegistrationService.registerCustomer(request);
            return ResponseEntity.ok(response);
        } catch (UsernameAlreadyUsedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/staff")
    public ResponseEntity<UserRegistrationResponse> registerStaff(@RequestBody UserRegistrationRequest request) {
        final UserRegistrationResponse response;
        try {
            response = userRegistrationService.registerStaff(request);
            return ResponseEntity.ok(response);
        } catch (UsernameAlreadyUsedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/admin")
    public ResponseEntity<UserRegistrationResponse> registerAdmin(@RequestBody UserRegistrationRequest request) {
        final UserRegistrationResponse response;
        try {
            response = userRegistrationService.registerAdmin(request);
            return ResponseEntity.ok(response);
        } catch (UsernameAlreadyUsedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (AdminAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

}

