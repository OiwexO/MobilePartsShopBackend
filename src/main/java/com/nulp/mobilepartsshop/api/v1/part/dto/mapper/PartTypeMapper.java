package com.nulp.mobilepartsshop.api.v1.part.dto.mapper;

import com.nulp.mobilepartsshop.api.utils.Mapper;
import com.nulp.mobilepartsshop.api.v1.part.dto.request.PartTypeRequest;
import com.nulp.mobilepartsshop.api.v1.part.dto.response.PartTypeResponse;
import com.nulp.mobilepartsshop.core.entity.part.PartType;

public class PartTypeMapper extends Mapper<PartType,PartTypeResponse> {

    @Override
    public PartTypeResponse toResponse(PartType entity) {
        return PartTypeResponse.builder()
                .id(entity.getId())
                .nameEn(entity.getNameEn())
                .nameUk(entity.getNameUk())
                .build();
    }

    public PartType toEntity(PartTypeRequest partTypeRequest) {
        return PartType.builder()
                .nameEn(partTypeRequest.getNameEn())
                .nameUk(partTypeRequest.getNameUk())
                .build();
    }

}
