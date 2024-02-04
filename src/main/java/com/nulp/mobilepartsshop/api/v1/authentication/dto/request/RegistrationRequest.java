package com.nulp.mobilepartsshop.api.v1.authentication.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {

    private String username;

    private String password;

    private String firstname;

    private String lastname;
}
