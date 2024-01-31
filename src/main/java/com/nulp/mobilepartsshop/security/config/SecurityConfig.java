package com.nulp.mobilepartsshop.security.config;

import com.nulp.mobilepartsshop.api.v1.auth.controller.AuthenticationController;
import com.nulp.mobilepartsshop.core.enums.UserRole;
import com.nulp.mobilepartsshop.security.filter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] WHITE_LIST_URL = {
            AuthenticationController.AUTHENTICATION_MAPPING + "/**",
    };

    private static final String[] SECURED_CUSTOMER_URL = {
            "/api/v1/demo/customer"
    };

    private static final String[] SECURED_STAFF_URL = {
            "/api/v1/demo/staff"
    };
    private static final String[] SECURED_ADMIN_URL = {
            "/api/v1/demo/admin"
    };

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                .requestMatchers("/api/v1/demo/**")
                                .hasAnyRole(UserRole.CUSTOMER.name())
//                                .requestMatchers(SECURED_STAFF_URL).hasAnyRole(UserRole.STAFF.name(), UserRole.ADMIN.name())
//                                .requestMatchers(SECURED_ADMIN_URL).hasAnyRole(UserRole.ADMIN.name())
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
