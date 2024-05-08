package com.nulp.mobilepartsshop.api.v1.authentication.service;

import com.nulp.mobilepartsshop.api.v1.authentication.dto.request.AuthorizationRequest;
import com.nulp.mobilepartsshop.api.v1.authentication.dto.request.RegistrationRequest;
import com.nulp.mobilepartsshop.api.v1.authentication.dto.response.AuthorizationResponse;
import com.nulp.mobilepartsshop.exception.authentication.InvalidPasswordException;
import com.nulp.mobilepartsshop.exception.authentication.UsernameAlreadyUsedException;
import com.nulp.mobilepartsshop.exception.authentication.UsernameNotFoundException;

public interface AuthenticationService {

    AuthorizationResponse register(RegistrationRequest request) throws UsernameAlreadyUsedException;

    AuthorizationResponse authorizeStaffOrAdmin(
            AuthorizationRequest request
    ) throws UsernameNotFoundException, InvalidPasswordException;

    AuthorizationResponse authorizeCustomer(
            AuthorizationRequest request
    ) throws UsernameNotFoundException, InvalidPasswordException;


}
