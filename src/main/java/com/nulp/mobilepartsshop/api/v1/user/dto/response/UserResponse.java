package com.nulp.mobilepartsshop.api.v1.user.dto.response;

import com.nulp.mobilepartsshop.core.enums.user.UserAuthority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;

    private String username;

    private String firstname;

    private String lastname;

    private UserAuthority authority;
}
