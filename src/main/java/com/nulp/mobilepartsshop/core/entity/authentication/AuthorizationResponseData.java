package com.nulp.mobilepartsshop.core.entity.authentication;

import com.nulp.mobilepartsshop.core.entity.user.User;
import lombok.Builder;

@Builder
public record AuthorizationResponseData(User user, String jwtToken) {}
