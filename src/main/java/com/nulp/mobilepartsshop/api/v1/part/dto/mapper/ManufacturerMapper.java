package com.nulp.mobilepartsshop.api.v1.part.dto.mapper;

import com.nulp.mobilepartsshop.api.utils.Mapper;
import com.nulp.mobilepartsshop.api.v1.part.dto.response.ManufacturerResponse;
import com.nulp.mobilepartsshop.core.entity.part.manufacturer.Manufacturer;

public class ManufacturerMapper extends Mapper<Manufacturer, ManufacturerResponse> {

    public ManufacturerMapper() {}

    @Override
    public ManufacturerResponse toResponse(Manufacturer entity) {
        return ManufacturerResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

}
