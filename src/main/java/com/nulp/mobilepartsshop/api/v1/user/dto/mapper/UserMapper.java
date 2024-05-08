package com.nulp.mobilepartsshop.api.v1.user.dto.mapper;

import com.nulp.mobilepartsshop.api.utils.Mapper;
import com.nulp.mobilepartsshop.api.v1.user.dto.response.UserResponse;
import com.nulp.mobilepartsshop.core.entity.user.User;

public class UserMapper extends Mapper<User, UserResponse> {

    @Override
    public UserResponse toResponse(User entity) {
        return UserResponse.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .firstname(entity.getFirstname())
                .lastname(entity.getLastname())
                .authority(entity.getAuthority())
                .build();
    }
}
