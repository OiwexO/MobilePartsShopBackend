package com.nulp.mobilepartsshop.api.v1.part.dto.mapper;

import com.nulp.mobilepartsshop.api.v1.part.dto.request.DeviceTypeRequest;
import com.nulp.mobilepartsshop.api.v1.part.dto.response.DeviceTypeResponse;
import com.nulp.mobilepartsshop.core.entity.part.DeviceType;

import java.util.List;

public class DeviceTypeMapper {

    public static DeviceTypeResponse toDto(DeviceType deviceType) {
        return DeviceTypeResponse.builder()
                .id(deviceType.getId())
                .nameEn(deviceType.getNameEn())
                .nameUk(deviceType.getNameUk())
                .build();
    }

    public static List<DeviceTypeResponse> toDtoList(List<DeviceType> deviceTypes) {
        return deviceTypes.stream()
                .map(DeviceTypeMapper::toDto)
                .toList();
    }

    public static DeviceType toEntity(DeviceTypeRequest deviceTypeRequest) {
        return DeviceType.builder()
                .nameEn(deviceTypeRequest.getNameEn())
                .nameUk(deviceTypeRequest.getNameUk())
                .build();
    }

}
