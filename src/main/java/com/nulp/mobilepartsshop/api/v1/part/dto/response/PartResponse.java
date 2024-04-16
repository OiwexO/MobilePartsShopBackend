package com.nulp.mobilepartsshop.api.v1.part.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartResponse {

    private Long id;

    private Double price;

    private Integer quantity;

    private String model;

    private String specifications;

    private ManufacturerResponse manufacturer;

    private DeviceTypeResponse deviceType;

    private PartTypeResponse partType;
}
