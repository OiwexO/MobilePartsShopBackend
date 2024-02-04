package com.nulp.mobilepartsshop.security.filter;

import com.nulp.mobilepartsshop.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";
    private static final int AUTHORIZATION_HEADER_PREFIX_LENGTH = AUTHORIZATION_HEADER_PREFIX.length();

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader(AUTHORIZATION_HEADER_NAME);
        if (authHeader == null || !authHeader.startsWith(AUTHORIZATION_HEADER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        final String jwt = extractToken(authHeader);
        final String username = jwtService.extractUsername(jwt);
        if (username == null || isUserAuthenticated()) {
            filterChain.doFilter(request, response);
            return;
        }
        final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        if (jwtService.isTokenValid(jwt, userDetails)) {
            authenticateUser(userDetails, request);
        }
        filterChain.doFilter(request, response);
    }

    private static String extractToken(final String authHeader) {
        return authHeader.substring(AUTHORIZATION_HEADER_PREFIX_LENGTH);
    }

    private boolean isUserAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null;
    }

    private void authenticateUser(final UserDetails userDetails, final HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
