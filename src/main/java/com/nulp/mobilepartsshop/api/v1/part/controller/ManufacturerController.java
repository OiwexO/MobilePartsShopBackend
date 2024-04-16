package com.nulp.mobilepartsshop.api.v1.part.controller;

import com.nulp.mobilepartsshop.api.utils.MultipartFileUtils;
import com.nulp.mobilepartsshop.api.v1.ApiConstants;
import com.nulp.mobilepartsshop.api.v1.part.dto.mapper.ManufacturerMapper;
import com.nulp.mobilepartsshop.api.v1.part.dto.request.ManufacturerRequest;
import com.nulp.mobilepartsshop.api.v1.part.dto.response.ManufacturerResponse;
import com.nulp.mobilepartsshop.api.v1.part.dto.validator.ManufacturerRequestValidator;
import com.nulp.mobilepartsshop.api.v1.part.service.manufacturer.ManufacturerService;
import com.nulp.mobilepartsshop.core.entity.part.manufacturer.Manufacturer;
import com.nulp.mobilepartsshop.core.entity.part.manufacturer.ManufacturerLogo;
import com.nulp.mobilepartsshop.exception.entity.EntityAlreadyExistsException;
import com.nulp.mobilepartsshop.exception.image.ImageDeleteException;
import com.nulp.mobilepartsshop.exception.image.ImageGetInputStreamException;
import com.nulp.mobilepartsshop.exception.image.ImageSaveException;
import com.nulp.mobilepartsshop.exception.image.ImageStoreException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ManufacturerController.MAPPING)
@RequiredArgsConstructor
public class ManufacturerController {

    public static final String MAPPING = ApiConstants.PART_MAPPING_V1 + "/manufacturers";

    public static final String GET_MANUFACTURER_LOGO_MAPPING = "/{manufacturerId}/logo";

    private final ManufacturerService manufacturerService;

    @GetMapping()
    public ResponseEntity<List<ManufacturerResponse>> getAllManufacturers() {
        List<Manufacturer> manufacturers = manufacturerService.getAllManufacturers();
        List<ManufacturerResponse> manufacturerResponses = ManufacturerMapper.toDtoList(manufacturers);
        return ResponseEntity.ok(manufacturerResponses);
    }

    @GetMapping("/{manufacturerId}")
    public ResponseEntity<ManufacturerResponse> getManufacturer(@PathVariable Long manufacturerId) {
        if (!ManufacturerRequestValidator.isValidId(manufacturerId)) {
            return ResponseEntity.badRequest().build();
        }
        return manufacturerService.getManufacturerById(manufacturerId)
                .map(ManufacturerMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(GET_MANUFACTURER_LOGO_MAPPING)
    @ResponseBody
    public ResponseEntity<InputStreamResource> getManufacturerLogo(@PathVariable Long manufacturerId) {
        if (!ManufacturerRequestValidator.isValidId(manufacturerId)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Manufacturer> optionalManufacturer = manufacturerService.getManufacturerById(manufacturerId);
        if (optionalManufacturer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ManufacturerLogo logo = optionalManufacturer.get().getLogo();
        try {
            InputStream in = manufacturerService.getManufacturerLogoInputStream(logo);
            return ResponseEntity.ok()
                    .contentType(MultipartFileUtils.MANUFACTURER_LOGO_MEDIA_TYPE)
                    .body(new InputStreamResource(in));
        } catch (ImageGetInputStreamException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping()
    public ResponseEntity<ManufacturerResponse> createManufacturer(@ModelAttribute ManufacturerRequest request) {
        if (!ManufacturerRequestValidator.isValidDto(request)) {
            return ResponseEntity.badRequest().build();
        }
        String name = request.getName();
        MultipartFile logo = request.getLogo();
        try {
            final Manufacturer manufacturer = manufacturerService.createManufacturer(name, logo);
            final ManufacturerResponse manufacturerResponse = ManufacturerMapper.toDto(manufacturer);
            return ResponseEntity.ok(manufacturerResponse);
        } catch (EntityAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (ImageSaveException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{manufacturerId}")
    public ResponseEntity<ManufacturerResponse> updateManufacturer(
            @PathVariable Long manufacturerId,
            @ModelAttribute ManufacturerRequest request
    ) {
        String name = request.getName();
        MultipartFile logo = request.getLogo();
        if (!ManufacturerRequestValidator.isValidId(manufacturerId) ||
                !ManufacturerRequestValidator.isValidName(name)) {
            return ResponseEntity.badRequest().build();
        }
        if (!logo.isEmpty()) {
            if (!MultipartFileUtils.isValidFileForManufacturerLogo(logo)) {
                return ResponseEntity.badRequest().build();
            }
        }
        try {
            return manufacturerService.updateManufacturer(manufacturerId, name, logo)
                    .map(ManufacturerMapper::toDto)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (ImageStoreException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{manufacturerId}")
    public ResponseEntity<Void> deleteManufacturer(@PathVariable Long manufacturerId) {
        if (!ManufacturerRequestValidator.isValidId(manufacturerId)) {
            return ResponseEntity.badRequest().build();
        }
        try {
            if (manufacturerService.deleteManufacturer(manufacturerId)) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (ImageDeleteException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
