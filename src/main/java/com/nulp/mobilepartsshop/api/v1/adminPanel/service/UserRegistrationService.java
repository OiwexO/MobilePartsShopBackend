package com.nulp.mobilepartsshop.api.v1.adminPanel.service;

import com.nulp.mobilepartsshop.api.v1.adminPanel.dto.request.UserRegistrationRequest;
import com.nulp.mobilepartsshop.api.v1.adminPanel.dto.response.UserRegistrationResponse;
import com.nulp.mobilepartsshop.exception.authentication.UsernameAlreadyUsedException;

public interface UserRegistrationService {

    UserRegistrationResponse register(UserRegistrationRequest request) throws UsernameAlreadyUsedException;
}
