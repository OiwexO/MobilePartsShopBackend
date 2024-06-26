package com.nulp.mobilepartsshop.api.v1.adminPanel.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRequest {

    private String username;

    private String password;

    private String firstname;

    private String lastname;
}
