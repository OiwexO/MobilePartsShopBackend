package com.nulp.mobilepartsshop.api.v1.manufacturer.controller;

import com.nulp.mobilepartsshop.api.utils.DtoValidator;
import com.nulp.mobilepartsshop.api.utils.ImageUtils;
import com.nulp.mobilepartsshop.api.v1.ApiConstants;
import com.nulp.mobilepartsshop.api.v1.manufacturer.dto.ManufacturerDto;
import com.nulp.mobilepartsshop.api.v1.manufacturer.dto.ManufacturerMapper;
import com.nulp.mobilepartsshop.api.v1.manufacturer.dto.request.ManufacturerRequest;
import com.nulp.mobilepartsshop.api.v1.manufacturer.service.ManufacturerService;
import com.nulp.mobilepartsshop.core.entity.manufacturer.Manufacturer;
import com.nulp.mobilepartsshop.core.entity.manufacturer.ManufacturerLogo;
import com.nulp.mobilepartsshop.core.enums.ImageType;
import com.nulp.mobilepartsshop.exception.image.ImageDeleteException;
import com.nulp.mobilepartsshop.exception.image.ImageGetInputStreamException;
import com.nulp.mobilepartsshop.exception.image.ImageSaveException;
import com.nulp.mobilepartsshop.exception.image.ImageStoreException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
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

    public static final String MAPPING = ApiConstants.GLOBAL_MAPPING_V1 + "/manufacturers";

    public static final String GET_MANUFACTURER_LOGO_MAPPING = "/{manufacturerId}/logo";

    private final ManufacturerService manufacturerService;

    private final HttpServletRequest httpServletRequest;

    @GetMapping()
    public ResponseEntity<List<ManufacturerDto>> getAllManufacturers() {
        List<Manufacturer> manufacturers = manufacturerService.getAllManufacturers();
        List<ManufacturerDto> manufacturerDtos = ManufacturerMapper.toDtoList(manufacturers, httpServletRequest);
        return ResponseEntity.ok(manufacturerDtos);
    }

    @GetMapping("/{manufacturerId}")
    public ResponseEntity<ManufacturerDto> getManufacturer(@PathVariable Long manufacturerId) {
        if (!DtoValidator.isValidId(manufacturerId)) {
            return ResponseEntity.badRequest().build();
        }
        return manufacturerService.getManufacturerById(manufacturerId)
                .map(manufacturer -> ManufacturerMapper.toDto(manufacturer, httpServletRequest))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(GET_MANUFACTURER_LOGO_MAPPING)
    @ResponseBody
    public ResponseEntity<InputStreamResource> getManufacturerLogo(@PathVariable Long manufacturerId) {
        if (!DtoValidator.isValidId(manufacturerId)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Manufacturer> optionalManufacturer = manufacturerService.getManufacturerById(manufacturerId);
        if (optionalManufacturer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ManufacturerLogo logo = optionalManufacturer.get().getLogo();
        MediaType contentType = logo.getImageType() == ImageType.JPG ? MediaType.IMAGE_JPEG : MediaType.IMAGE_PNG;
        try {
            InputStream in = manufacturerService.getManufacturerLogoInputStream(logo);
            return ResponseEntity.ok()
                    .contentType(contentType)
                    .body(new InputStreamResource(in));
        } catch (ImageGetInputStreamException e) {
            return ResponseEntity.internalServerError().build();
        }
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
            final ManufacturerDto manufacturerDto = ManufacturerMapper.toDto(manufacturer, httpServletRequest);
            return ResponseEntity.ok(manufacturerDto);
        } catch (ImageSaveException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{manufacturerId}")
    public ResponseEntity<ManufacturerDto> updateManufacturer(
            @PathVariable Long manufacturerId,
            @ModelAttribute ManufacturerRequest request
    ) {
        String name = request.getName();
        MultipartFile logo = request.getLogo();
        ImageType imageType = ImageUtils.getImageType(logo);
        if (!DtoValidator.isValidId(manufacturerId) || name == null || name.isBlank() || imageType == ImageType.UNSUPPORTED) {
            return ResponseEntity.badRequest().build();
        }
        try {
            return manufacturerService.updateManufacturer(manufacturerId, name, logo, imageType)
                    .map(manufacturer -> ManufacturerMapper.toDto(manufacturer, httpServletRequest))
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (ImageStoreException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{manufacturerId}")
    public ResponseEntity<Void> deleteManufacturer(@PathVariable Long manufacturerId) {
        if (!DtoValidator.isValidId(manufacturerId)) {
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
