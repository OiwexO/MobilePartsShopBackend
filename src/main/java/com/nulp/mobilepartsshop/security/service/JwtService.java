package com.nulp.mobilepartsshop.security.service;

import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(final UserDetails userDetails);

    boolean isTokenValid(final String jwtToken, final UserDetails userDetails);

    @Nullable
    String extractUsername(final String jwtToken);
}
