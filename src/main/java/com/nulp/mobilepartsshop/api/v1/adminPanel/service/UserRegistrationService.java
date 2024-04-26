package com.nulp.mobilepartsshop.api.v1.adminPanel.service;

import com.nulp.mobilepartsshop.api.v1.adminPanel.dto.request.UserRegistrationRequest;
import com.nulp.mobilepartsshop.api.v1.adminPanel.dto.response.UserRegistrationResponse;
import com.nulp.mobilepartsshop.exception.adminPanel.AdminAlreadyExistsException;
import com.nulp.mobilepartsshop.exception.authentication.UsernameAlreadyUsedException;

public interface UserRegistrationService {

    UserRegistrationResponse registerCustomer(UserRegistrationRequest request) throws UsernameAlreadyUsedException;

    UserRegistrationResponse registerStaff(UserRegistrationRequest request) throws UsernameAlreadyUsedException;

    UserRegistrationResponse registerAdmin(UserRegistrationRequest request) throws UsernameAlreadyUsedException, AdminAlreadyExistsException;
}
