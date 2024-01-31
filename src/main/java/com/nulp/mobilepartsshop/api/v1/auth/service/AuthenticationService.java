package com.nulp.mobilepartsshop.api.v1.auth.service;

import com.nulp.mobilepartsshop.api.v1.auth.dto.request.AuthenticationRequest;
import com.nulp.mobilepartsshop.api.v1.auth.dto.request.RegistrationRequest;
import com.nulp.mobilepartsshop.api.v1.auth.dto.response.AuthenticationResponse;
import com.nulp.mobilepartsshop.exception.auth.InvalidPasswordException;
import com.nulp.mobilepartsshop.exception.auth.UsernameAlreadyUsedException;
import com.nulp.mobilepartsshop.exception.auth.UsernameNotFoundException;

public interface AuthenticationService {

    AuthenticationResponse register(RegistrationRequest request) throws UsernameAlreadyUsedException;

    AuthenticationResponse authenticate(AuthenticationRequest request) throws UsernameNotFoundException, InvalidPasswordException;
}
