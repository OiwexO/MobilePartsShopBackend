package com.nulp.mobilepartsshop.api.v1.part.dto.mapper;

import com.nulp.mobilepartsshop.api.utils.Mapper;
import com.nulp.mobilepartsshop.api.v1.part.dto.response.PartResponse;
import com.nulp.mobilepartsshop.core.entity.part.Part;

public class PartMapper extends Mapper<Part, PartResponse> {

    private final ManufacturerMapper manufacturerMapper = new ManufacturerMapper();

    private final DeviceTypeMapper deviceTypeMapper = new DeviceTypeMapper();

    private final PartTypeMapper partTypeMapper = new PartTypeMapper();

    public PartMapper() {}

    @Override
    public PartResponse toResponse(Part entity) {
        return PartResponse.builder()
                .id(entity.getId())
                .price(entity.getPrice())
                .quantity(entity.getQuantity())
                .name(entity.getName())
                .deviceModels(entity.getDeviceModels())
                .specifications(entity.getSpecifications())
                .manufacturer(manufacturerMapper.toResponse(entity.getManufacturer()))
                .deviceType(deviceTypeMapper.toResponse(entity.getDeviceType()))
                .partType(partTypeMapper.toResponse(entity.getPartType()))
                .build();
    }
}
