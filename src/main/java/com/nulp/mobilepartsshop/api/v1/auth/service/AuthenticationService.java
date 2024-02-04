package com.nulp.mobilepartsshop.api.v1.auth.service;

import com.nulp.mobilepartsshop.api.v1.auth.dto.request.AuthorizationRequest;
import com.nulp.mobilepartsshop.api.v1.auth.dto.request.RegistrationRequest;
import com.nulp.mobilepartsshop.api.v1.auth.dto.response.AuthorizationResponse;
import com.nulp.mobilepartsshop.exception.authentication.InvalidPasswordException;
import com.nulp.mobilepartsshop.exception.authentication.UsernameAlreadyUsedException;
import com.nulp.mobilepartsshop.exception.authentication.UsernameNotFoundException;

public interface AuthenticationService {

    AuthorizationResponse register(RegistrationRequest request) throws UsernameAlreadyUsedException;

    AuthorizationResponse authorize(
            AuthorizationRequest request
    ) throws UsernameNotFoundException, InvalidPasswordException;
}
