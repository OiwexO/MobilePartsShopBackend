package com.nulp.mobilepartsshop.api.v1.authentication.service;

import com.nulp.mobilepartsshop.core.entity.authentication.AuthorizationData;
import com.nulp.mobilepartsshop.core.entity.authentication.AuthorizationResponseData;
import com.nulp.mobilepartsshop.core.entity.authentication.RegistrationData;
import com.nulp.mobilepartsshop.exception.authentication.InvalidPasswordException;
import com.nulp.mobilepartsshop.exception.authentication.UsernameAlreadyUsedException;
import com.nulp.mobilepartsshop.exception.authentication.UsernameNotFoundException;

public interface AuthenticationService {

    AuthorizationResponseData register(RegistrationData data) throws UsernameAlreadyUsedException;

    AuthorizationResponseData authorizeStaffOrAdmin(
            AuthorizationData data
    ) throws UsernameNotFoundException, InvalidPasswordException;

    AuthorizationResponseData authorizeCustomer(
            AuthorizationData data
    ) throws UsernameNotFoundException, InvalidPasswordException;


}
