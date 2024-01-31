package com.nulp.mobilepartsshop.security.filter;

import com.nulp.mobilepartsshop.domain.model.user.User;
import com.nulp.mobilepartsshop.domain.service.JwtService;
import com.nulp.mobilepartsshop.domain.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private static final String AUTH_HEADER_NAME = "Authorization";
    private static final String AUTH_HEADER_PREFIX = "Bearer ";
    private static final int AUTH_HEADER_PREFIX_LENGTH = AUTH_HEADER_PREFIX.length();

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader(AUTH_HEADER_NAME);
        final String jwt;
        final String username;
        if (authHeader == null || !authHeader.startsWith(AUTH_HEADER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = extractToken(authHeader);
        username = jwtService.extractUsername(jwt);
        if (username != null && !isUserAuthenticated()) {
            User user = this.userService.loadUserByUsername(username);
            if (jwtService.isTokenValid(jwt, user)) {
                authenticateUser(user, request);
            }
        }
        filterChain.doFilter(request, response);
    }

    private static String extractToken(String authHeader) {
        return authHeader.substring(AUTH_HEADER_PREFIX_LENGTH);
    }

    private boolean isUserAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }

    private void authenticateUser(User user, @NonNull HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                user,
                null,
                user.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
