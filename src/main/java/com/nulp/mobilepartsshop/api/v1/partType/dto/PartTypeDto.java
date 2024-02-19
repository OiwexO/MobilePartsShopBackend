package com.nulp.mobilepartsshop.api.v1.partType.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartTypeDto {

    private Long id;

    private String nameEn;

    private String nameUk;
}
