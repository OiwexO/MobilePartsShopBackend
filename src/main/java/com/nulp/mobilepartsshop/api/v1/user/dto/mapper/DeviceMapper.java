package com.nulp.mobilepartsshop.api.v1.user.dto.mapper;

import com.nulp.mobilepartsshop.api.utils.Mapper;
import com.nulp.mobilepartsshop.api.v1.part.dto.mapper.DeviceTypeMapper;
import com.nulp.mobilepartsshop.api.v1.part.dto.mapper.ManufacturerMapper;
import com.nulp.mobilepartsshop.api.v1.user.dto.response.DeviceResponse;
import com.nulp.mobilepartsshop.core.entity.user.Device;

public class DeviceMapper extends Mapper<Device, DeviceResponse> {

    private final ManufacturerMapper manufacturerMapper = new ManufacturerMapper();

    private final DeviceTypeMapper deviceTypeMapper = new DeviceTypeMapper();

    public DeviceMapper() {}

    @Override
    public DeviceResponse toResponse(Device entity) {
        return DeviceResponse.builder()
                .id(entity.getId())
                .model(entity.getModel())
                .specifications(entity.getSpecifications())
                .manufacturer(manufacturerMapper.toResponse(entity.getManufacturer()))
                .deviceType(deviceTypeMapper.toResponse(entity.getDeviceType()))
                .build();
    }
}
