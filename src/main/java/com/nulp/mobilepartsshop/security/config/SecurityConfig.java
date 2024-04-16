package com.nulp.mobilepartsshop.security.config;

import com.nulp.mobilepartsshop.api.v1.adminPanel.controller.UserRegistrationController;
import com.nulp.mobilepartsshop.api.v1.authentication.controller.AuthenticationController;
import com.nulp.mobilepartsshop.api.v1.part.controller.DeviceTypeController;
import com.nulp.mobilepartsshop.api.v1.part.controller.ManufacturerController;
import com.nulp.mobilepartsshop.api.v1.part.controller.PartController;
import com.nulp.mobilepartsshop.api.v1.part.controller.PartTypeController;
import com.nulp.mobilepartsshop.core.enums.UserAuthority;
import com.nulp.mobilepartsshop.security.filter.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

    private static final String[] WHITE_LIST_MAPPINGS = {
            AuthenticationController.MAPPING + "/**",
    };

    private static final String[] PART_MAPPINGS = {
            DeviceTypeController.MAPPING + "/**",
            ManufacturerController.MAPPING + "/**",
            PartTypeController.MAPPING + "/**",
            PartController.MAPPING + "/**",
    };

    private static final String[] SECURED_CUSTOMER_URL = {
            "/api/v1/demo/customer"
    };

    private static final String[] SECURED_STAFF_URL = {
            "/api/v1/demo/staff"
    };
    private static final String[] SECURED_ADMIN_URL = {
            "/api/v1/demo/admin",
            UserRegistrationController.MAPPING + "/**",
    };

    private static final String[] AUTHORITY_CUSTOMER_OR_HIGHER = {
            UserAuthority.CUSTOMER.name(),
            UserAuthority.STAFF.name(),
            UserAuthority.ADMIN.name()
    };

    private static final String[] AUTHORITY_STAFF_OR_HIGHER = {
            UserAuthority.STAFF.name(),
            UserAuthority.ADMIN.name()
    };
    private static final String[] AUTHORITY_ADMIN_OR_HIGHER = {
            UserAuthority.ADMIN.name()
    };

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers(HttpMethod.GET, WHITE_LIST_MAPPINGS).permitAll()
                                .requestMatchers(HttpMethod.GET, PART_MAPPINGS).permitAll()
                                .requestMatchers(HttpMethod.POST, PART_MAPPINGS).hasAnyAuthority(AUTHORITY_STAFF_OR_HIGHER)
                                .requestMatchers(HttpMethod.PUT, PART_MAPPINGS).hasAnyAuthority(AUTHORITY_STAFF_OR_HIGHER)
                                .requestMatchers(HttpMethod.DELETE, PART_MAPPINGS).hasAnyAuthority(AUTHORITY_STAFF_OR_HIGHER)
                                .requestMatchers(SECURED_CUSTOMER_URL).hasAnyAuthority(AUTHORITY_CUSTOMER_OR_HIGHER)
                                .requestMatchers(SECURED_STAFF_URL).hasAnyAuthority(AUTHORITY_STAFF_OR_HIGHER)
                                .requestMatchers(SECURED_ADMIN_URL).hasAnyAuthority(AUTHORITY_ADMIN_OR_HIGHER)
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
