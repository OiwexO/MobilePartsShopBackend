package com.nulp.mobilepartsshop.api.v1.manufacturer.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetManufacturerRequest {

    private Long id;
}
