package com.nulp.mobilepartsshop.api.v1.part.dto.mapper;

import com.nulp.mobilepartsshop.api.v1.part.dto.request.PartTypeRequest;
import com.nulp.mobilepartsshop.api.v1.part.dto.response.PartTypeResponse;
import com.nulp.mobilepartsshop.core.entity.part.PartType;

import java.util.List;

public class PartTypeMapper {

    public static PartTypeResponse toDto(PartType partType) {
        return PartTypeResponse.builder()
                .id(partType.getId())
                .nameEn(partType.getNameEn())
                .nameUk(partType.getNameUk())
                .build();
    }

    public static List<PartTypeResponse> toDtoList(List<PartType> partTypes) {
        return partTypes.stream()
                .map(PartTypeMapper::toDto)
                .toList();
    }

    public static PartType toEntity(PartTypeRequest partTypeRequest) {
        return PartType.builder()
                .nameEn(partTypeRequest.getNameEn())
                .nameUk(partTypeRequest.getNameUk())
                .build();
    }

}
