package com.nulp.mobilepartsshop.api.v1.user.dto.mapper;

import com.nulp.mobilepartsshop.api.utils.Mapper;
import com.nulp.mobilepartsshop.api.v1.user.dto.response.AddressResponse;
import com.nulp.mobilepartsshop.core.entity.user.Address;

public class AddressMapper extends Mapper<Address, AddressResponse> {

    public AddressMapper() {}

    @Override
    public AddressResponse toResponse(Address entity) {
        return AddressResponse.builder()
                .id(entity.getId())
                .postalCode(entity.getPostalCode())
                .country(entity.getCountry())
                .state(entity.getState())
                .city(entity.getCity())
                .street(entity.getStreet())
                .buildingNumber(entity.getBuildingNumber())
                .build();
    }
}
