package com.nulp.mobilepartsshop.api.v1.manufacturer.controller;

import com.nulp.mobilepartsshop.api.v1.ApiConstants;
import com.nulp.mobilepartsshop.api.v1.manufacturer.dto.ManufacturerDto;
import com.nulp.mobilepartsshop.api.v1.manufacturer.dto.request.ManufacturerRequest;
import com.nulp.mobilepartsshop.api.v1.manufacturer.service.ManufacturerService;
import com.nulp.mobilepartsshop.api.v1.mapper.ManufacturerMapper;
import com.nulp.mobilepartsshop.core.entity.manufacturer.ImageType;
import com.nulp.mobilepartsshop.core.entity.manufacturer.Manufacturer;
import com.nulp.mobilepartsshop.core.entity.manufacturer.ManufacturerLogo;
import com.nulp.mobilepartsshop.exception.image.ImageDeleteException;
import com.nulp.mobilepartsshop.exception.image.ImageSaveException;
import com.nulp.mobilepartsshop.exception.image.ImageStoreException;
import com.nulp.mobilepartsshop.exception.manufacturer.ManufacturerNotFoundException;
import com.nulp.mobilepartsshop.utils.ImageUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ManufacturerController.MAPPING)
@RequiredArgsConstructor
public class ManufacturerController {

    public static final String MAPPING = ApiConstants.GLOBAL_MAPPING + "/manufacturers";

    public static final String GET_MANUFACTURER_MAPPING = "/{manufacturerId}";

    public static final String GET_MANUFACTURER_LOGO_MAPPING = "/{manufacturerId}/logo";

    public static final String PUT_MANUFACTURER_MAPPING = "/{manufacturerId}";

    public static final String DELETE_MANUFACTURER_MAPPING = "/{manufacturerId}";

    private final ManufacturerService manufacturerService;

    private final HttpServletRequest httpServletRequest;

    @GetMapping()
    public ResponseEntity<List<ManufacturerDto>> getAllManufacturers() {
        List<Manufacturer> manufacturers = manufacturerService.getAllManufacturers();
        List<ManufacturerDto> manufacturerDtos = manufacturers.stream()
                .map(manufacturer -> ManufacturerMapper.toDto(httpServletRequest, manufacturer))
                .collect(Collectors.toList());
        return ResponseEntity.ok(manufacturerDtos);
    }

    @GetMapping(GET_MANUFACTURER_MAPPING)
    public ResponseEntity<ManufacturerDto> getManufacturer(@PathVariable Long manufacturerId) {
        if (manufacturerId == null) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Manufacturer> optionalManufacturer = manufacturerService.getManufacturerById(manufacturerId);
        if (optionalManufacturer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Manufacturer manufacturer = optionalManufacturer.get();
        ManufacturerDto manufacturerDto = ManufacturerMapper.toDto(httpServletRequest, manufacturer);
        return ResponseEntity.ok(manufacturerDto);
    }

    //TODO refactor
    @GetMapping(GET_MANUFACTURER_LOGO_MAPPING)
    @ResponseBody
    public ResponseEntity<InputStreamResource> getManufacturerLogo(@PathVariable Long manufacturerId) {
        if (manufacturerId == null) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Manufacturer> optionalManufacturer = manufacturerService.getManufacturerById(manufacturerId);
        if (optionalManufacturer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ManufacturerLogo logo = optionalManufacturer.get().getLogo();
        MediaType contentType = logo.getImageType() == ImageType.JPG ? MediaType.IMAGE_JPEG : MediaType.IMAGE_PNG;
        final Path targetPath = Paths.get(logo.getFilepath()).toAbsolutePath();
        InputStream in;
        try {
            in = Files.newInputStream(targetPath);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok()
                .contentType(contentType)
                .body(new InputStreamResource(in));
    }

    @PostMapping()
    public ResponseEntity<ManufacturerDto> createManufacturer(@ModelAttribute ManufacturerRequest request) {
        String name = request.getName();
        MultipartFile logo = request.getLogo();
        ImageType imageType = ImageUtils.getImageType(logo);
        if (name == null || name.isBlank() || logo.isEmpty() || imageType == ImageType.UNSUPPORTED) {
            return ResponseEntity.badRequest().build();
        }
        try {
            final Manufacturer manufacturer = manufacturerService.createManufacturer(name, logo, imageType);
            final ManufacturerDto manufacturerDto = ManufacturerMapper.toDto(httpServletRequest, manufacturer);
            return ResponseEntity.ok(manufacturerDto);
        } catch (ImageSaveException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(PUT_MANUFACTURER_MAPPING)
    public ResponseEntity<ManufacturerDto> updateManufacturer(
            @PathVariable Long manufacturerId,
            @ModelAttribute ManufacturerRequest request
    ) {
        String name = request.getName();
        MultipartFile logo = request.getLogo();
        ImageType imageType = ImageUtils.getImageType(logo);
        if (manufacturerId == null || name == null || name.isBlank() || imageType == ImageType.UNSUPPORTED) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Manufacturer manufacturer = manufacturerService.updateManufacturer(manufacturerId, name, logo, imageType);
            ManufacturerDto manufacturerDto = ManufacturerMapper.toDto(httpServletRequest, manufacturer);
            return ResponseEntity.ok(manufacturerDto);
        } catch (ManufacturerNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ImageStoreException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(DELETE_MANUFACTURER_MAPPING)
    public ResponseEntity<Void> deleteManufacturer(@PathVariable Long manufacturerId) {
        if (manufacturerId == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            manufacturerService.deleteManufacturer(manufacturerId);
        } catch (ManufacturerNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ImageDeleteException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.noContent().build();
    }
}