package com.nulp.mobilepartsshop.api.v1.partType.dto;

import com.nulp.mobilepartsshop.core.entity.partType.PartType;

import java.util.List;

public class PartTypeMapper {

    public static PartTypeDto toDto(PartType partType) {
        return PartTypeDto.builder()
                .id(partType.getId())
                .nameEn(partType.getNameEn())
                .nameUk(partType.getNameUk())
                .build();
    }

    public static List<PartTypeDto> toDtoList(List<PartType> partTypes) {
        return partTypes.stream()
                .map(PartTypeMapper::toDto)
                .toList();
    }

    public static PartType toEntity(PartTypeDto partTypeDto) {
        return PartType.builder()
                .id(partTypeDto.getId())
                .nameEn(partTypeDto.getNameEn())
                .nameUk(partTypeDto.getNameUk())
                .build();
    }

}
