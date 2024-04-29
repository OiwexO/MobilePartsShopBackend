package com.nulp.mobilepartsshop.api.v1.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceRequest {

    private String model;

    private String specifications;

    private Long manufacturerId;

    private Long deviceTypeId;
}
