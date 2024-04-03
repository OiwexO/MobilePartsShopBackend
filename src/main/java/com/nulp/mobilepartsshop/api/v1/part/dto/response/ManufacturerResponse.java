package com.nulp.mobilepartsshop.api.v1.part.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerResponse {

    private Long id;

    private String name;

    private String logoUrl;
}
