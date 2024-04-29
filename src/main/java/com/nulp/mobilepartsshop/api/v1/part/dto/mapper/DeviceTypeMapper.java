package com.nulp.mobilepartsshop.api.v1.part.dto.mapper;

import com.nulp.mobilepartsshop.api.utils.Mapper;
import com.nulp.mobilepartsshop.api.v1.part.dto.request.DeviceTypeRequest;
import com.nulp.mobilepartsshop.api.v1.part.dto.response.DeviceTypeResponse;
import com.nulp.mobilepartsshop.core.entity.part.DeviceType;

public class DeviceTypeMapper extends Mapper<DeviceType, DeviceTypeResponse> {

    public DeviceTypeMapper() {}

    @Override
    public DeviceTypeResponse toResponse(DeviceType entity) {
        return DeviceTypeResponse.builder()
                .id(entity.getId())
                .nameEn(entity.getNameEn())
                .nameUk(entity.getNameUk())
                .build();
    }

    public DeviceType toEntity(DeviceTypeRequest deviceTypeRequest) {
        return DeviceType.builder()
                .nameEn(deviceTypeRequest.getNameEn())
                .nameUk(deviceTypeRequest.getNameUk())
                .build();
    }

}
