package com.nulp.mobilepartsshop.api.v1.part.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceTypeResponse {

    private Long id;

    private String nameEn;

    private String nameUk;
}