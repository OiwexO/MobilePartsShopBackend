package com.nulp.mobilepartsshop.core.entity.authentication;

import com.nulp.mobilepartsshop.core.entity.user.User;
import lombok.Builder;

@Builder
public record RegistrationData(String username, String password, String firstname, String lastname) {}
