package com.nulp.mobilepartsshop.api.v1.authentication.dto.response;

import com.nulp.mobilepartsshop.api.v1.user.dto.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizationResponse {

    private UserResponse user;

    private String jwtToken;
}

