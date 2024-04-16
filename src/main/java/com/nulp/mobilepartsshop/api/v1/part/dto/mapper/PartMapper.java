package com.nulp.mobilepartsshop.api.v1.part.dto.mapper;

import com.nulp.mobilepartsshop.api.v1.part.dto.response.PartResponse;
import com.nulp.mobilepartsshop.core.entity.part.Part;
import com.nulp.mobilepartsshop.core.entity.part.PartImage;

import java.util.List;
import java.util.stream.Collectors;

public class PartMapper {

    public static PartResponse toDto(Part part) {
        final List<Long> imageIds = part.getPartImages().stream().map(PartImage::getId).collect(Collectors.toList());
        return PartResponse.builder()
                .id(part.getId())
                .price(part.getPrice())
                .quantity(part.getQuantity())
                .model(part.getModel())
                .specifications(part.getSpecifications())
                .manufacturer(ManufacturerMapper.toDto(part.getManufacturer()))
                .deviceType(DeviceTypeMapper.toDto(part.getDeviceType()))
                .partType(PartTypeMapper.toDto(part.getPartType()))
                .imageIds(imageIds)
                .build();
    }

    public static List<PartResponse> toDtoList(List<Part> parts) {
        return parts.stream()
                .map(PartMapper::toDto)
                .collect(Collectors.toList());
    }
}
