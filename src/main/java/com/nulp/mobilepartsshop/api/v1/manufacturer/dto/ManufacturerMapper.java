package com.nulp.mobilepartsshop.api.v1.manufacturer.dto;

import com.nulp.mobilepartsshop.api.v1.manufacturer.controller.ManufacturerController;
import com.nulp.mobilepartsshop.core.entity.manufacturer.Manufacturer;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

public class ManufacturerMapper {

    public static ManufacturerDto toDto(Manufacturer manufacturer, HttpServletRequest request) {
        final String logoUrl = getLogoUrl(request, manufacturer.getId());
        return ManufacturerDto.builder()
                .id(manufacturer.getId())
                .name(manufacturer.getName())
                .logoUrl(logoUrl)
                .build();
    }

    public static List<ManufacturerDto> toDtoList(List<Manufacturer> manufacturers, HttpServletRequest request) {
        return manufacturers.stream()
                .map(manufacturer -> ManufacturerMapper.toDto(manufacturer, request))
                .toList();
    }

    private static String getLogoUrl(HttpServletRequest request, Long manufacturerId) {
        return ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(ManufacturerController.MAPPING)
                .path(request.getContextPath())
                .path(ManufacturerController.GET_MANUFACTURER_LOGO_MAPPING)
                .buildAndExpand(manufacturerId)
                .toUriString();
    }
}
