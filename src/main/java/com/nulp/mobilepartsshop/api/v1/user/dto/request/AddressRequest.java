package com.nulp.mobilepartsshop.api.v1.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {

    private Integer postalCode;

    private String country;

    private String state;

    private String city;

    private String street;

    private String buildingNumber;
}
