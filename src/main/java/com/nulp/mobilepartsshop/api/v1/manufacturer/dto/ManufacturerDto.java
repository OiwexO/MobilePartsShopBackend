package com.nulp.mobilepartsshop.api.v1.manufacturer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerDto {

    private Long id;

    private String name;

    private String logoUrl;
}
