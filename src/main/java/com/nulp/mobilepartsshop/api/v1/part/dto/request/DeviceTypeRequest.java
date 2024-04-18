package com.nulp.mobilepartsshop.api.v1.part.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceTypeRequest {

    private String nameEn;

    private String nameUk;
}
