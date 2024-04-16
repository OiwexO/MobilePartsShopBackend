package com.nulp.mobilepartsshop.api.v1.part.dto.mapper;

import com.nulp.mobilepartsshop.api.v1.part.dto.response.ManufacturerResponse;
import com.nulp.mobilepartsshop.core.entity.part.manufacturer.Manufacturer;

import java.util.List;
import java.util.stream.Collectors;

public class ManufacturerMapper {

    public static ManufacturerResponse toDto(Manufacturer manufacturer) {
        return ManufacturerResponse.builder()
                .id(manufacturer.getId())
                .name(manufacturer.getName())
                .build();
    }

    public static List<ManufacturerResponse> toDtoList(List<Manufacturer> manufacturers) {
        return manufacturers.stream()
                .map(ManufacturerMapper::toDto)
                .collect(Collectors.toList());
    }

}
