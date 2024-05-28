package com.nulp.mobilepartsshop.core.entity.authentication;

import lombok.Builder;

@Builder
public record AuthorizationData(String username, String password) {}
