package com.nulp.mobilepartsshop.api.v1.manufacturer.controller;

import com.nulp.mobilepartsshop.api.v1.ApiConstants;
import com.nulp.mobilepartsshop.api.v1.manufacturer.dto.request.ManufacturerRequest;
import com.nulp.mobilepartsshop.api.v1.manufacturer.dto.ManufacturerDto;
import com.nulp.mobilepartsshop.api.v1.manufacturer.service.ManufacturerService;
import com.nulp.mobilepartsshop.core.entity.manufacturer.ImageType;
import com.nulp.mobilepartsshop.core.entity.manufacturer.Manufacturer;
import com.nulp.mobilepartsshop.core.entity.manufacturer.ManufacturerLogo;
import com.nulp.mobilepartsshop.exception.image.ImageDeleteException;
import com.nulp.mobilepartsshop.exception.image.ImageSaveException;
import com.nulp.mobilepartsshop.exception.image.ImageStoreException;
import com.nulp.mobilepartsshop.exception.manufacturer.ManufacturerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.InvalidMediaTypeException;
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

    private final ManufacturerService manufacturerService;

    @GetMapping()
    public ResponseEntity<List<ManufacturerDto>> getManufacturer() {
        List<Manufacturer> manufacturers = manufacturerService.getAllManufacturers();
        List<ManufacturerDto> manufacturerDtos = manufacturers.stream()
                .map(this::mapToManufacturerDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(manufacturerDtos);
    }

    @GetMapping("/{manufacturerId}")
    public ResponseEntity<ManufacturerDto> getManufacturer(@PathVariable Long manufacturerId) {
        if (manufacturerId == null) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Manufacturer> optionalManufacturer = manufacturerService.getManufacturerById(manufacturerId);
        if (optionalManufacturer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Manufacturer manufacturer = optionalManufacturer.get();
        ManufacturerDto manufacturerDto = mapToManufacturerDto(manufacturer);
        return ResponseEntity.ok(manufacturerDto);
    }

    //TODO refactor
    @GetMapping("/{manufacturerId}/logo")
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
        ImageType imageType = getImageType(logo);
        if (name == null || name.isBlank() || logo.isEmpty() || imageType == ImageType.UNSUPPORTED) {
            return ResponseEntity.badRequest().build();
        }
        try {
            final Manufacturer manufacturer = manufacturerService.createManufacturer(name, logo, imageType);
            final ManufacturerDto manufacturerDto = mapToManufacturerDto(manufacturer);
            return ResponseEntity.ok(manufacturerDto);
        } catch (ImageSaveException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{manufacturerId}")
    public ResponseEntity<ManufacturerDto> updateManufacturer(@PathVariable Long manufacturerId, @ModelAttribute ManufacturerRequest request) {
        String name = request.getName();
        MultipartFile logo = request.getLogo();
        ImageType imageType = getImageType(logo);
        if (manufacturerId == null || name == null || name.isBlank() || imageType == ImageType.UNSUPPORTED) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Manufacturer manufacturer = manufacturerService.updateManufacturer(manufacturerId, name, logo, imageType);
            ManufacturerDto manufacturerDto = mapToManufacturerDto(manufacturer);
            return ResponseEntity.ok(manufacturerDto);
        } catch (ManufacturerNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ImageStoreException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{manufacturerId}")
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

    //TODO remove or rewrite logoUrl
    private ManufacturerDto mapToManufacturerDto(Manufacturer manufacturer) {
        String logoUrl;
        try {
            final Path logoPath = Paths.get(manufacturer.getLogo().getFilepath()).toAbsolutePath();
            logoUrl = new UrlResource(logoPath.toUri()).toString();
        } catch (Exception e) {
            logoUrl = "";
        }
        return ManufacturerDto.builder()
                .id(manufacturer.getId())
                .name(manufacturer.getName())
                .logoUrl(logoUrl)
                .build();
    }

    public ImageType getImageType(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return ImageType.UNSUPPORTED;
        }
        String contentType = file.getContentType();
        if (contentType == null) {
            return ImageType.UNSUPPORTED;
        }
        try {
            MediaType mediaType = MediaType.parseMediaType(file.getContentType());
            if (mediaType.equals(MediaType.IMAGE_JPEG)) {
                return ImageType.JPG;
            } else if (mediaType.equals(MediaType.IMAGE_PNG)) {
                return ImageType.PNG;
            } else {
                return ImageType.UNSUPPORTED;
            }
        } catch (InvalidMediaTypeException e) {
            return ImageType.UNSUPPORTED;
        }
    }
}