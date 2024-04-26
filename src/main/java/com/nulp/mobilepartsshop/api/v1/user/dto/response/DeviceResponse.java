package com.nulp.mobilepartsshop.api.v1.user.dto.response;

import com.nulp.mobilepartsshop.api.v1.part.dto.response.DeviceTypeResponse;
import com.nulp.mobilepartsshop.api.v1.part.dto.response.ManufacturerResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceResponse {

    private Long id;

    private String model;

    private String specifications;

    private ManufacturerResponse manufacturer;

    private DeviceTypeResponse deviceType;
}
