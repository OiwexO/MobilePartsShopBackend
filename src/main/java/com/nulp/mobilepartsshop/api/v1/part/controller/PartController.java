package com.nulp.mobilepartsshop.api.v1.part.controller;

import com.nulp.mobilepartsshop.api.utils.MultipartFileUtils;
import com.nulp.mobilepartsshop.api.v1.ApiConstants;
import com.nulp.mobilepartsshop.api.v1.part.dto.mapper.PartMapper;
import com.nulp.mobilepartsshop.api.v1.part.dto.request.PartRequest;
import com.nulp.mobilepartsshop.api.v1.part.dto.response.PartResponse;
import com.nulp.mobilepartsshop.api.v1.part.dto.validator.PartRequestValidator;
import com.nulp.mobilepartsshop.api.v1.part.service.PartService;
import com.nulp.mobilepartsshop.core.entity.part.Part;
import com.nulp.mobilepartsshop.core.entity.part.PartImage;
import com.nulp.mobilepartsshop.exception.entity.EntityAlreadyExistsException;
import com.nulp.mobilepartsshop.exception.entity.EntityNotFoundException;
import com.nulp.mobilepartsshop.exception.image.ImageDeleteException;
import com.nulp.mobilepartsshop.exception.image.ImageGetInputStreamException;
import com.nulp.mobilepartsshop.exception.image.ImageSaveException;
import com.nulp.mobilepartsshop.exception.image.ImageStoreException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(PartController.MAPPING)
@RequiredArgsConstructor
public class PartController {

    public static final String MAPPING = ApiConstants.PART_MAPPING_V1 + "/parts";

    public static final String GET_PART_IMAGE_MAPPING = "/{partId}/{imageId}";

    private final PartService partService;

    @GetMapping
    public ResponseEntity<List<PartResponse>> getAllParts() {
        List<Part> parts = partService.getAllParts();
        List<PartResponse> partResponses = PartMapper.toDtoList(parts);
        return ResponseEntity.ok(partResponses);
    }

    @GetMapping("/{partId}")
    public ResponseEntity<PartResponse> getPart(@PathVariable Long partId) {
        if (!PartRequestValidator.isValidId(partId)) {
            return ResponseEntity.badRequest().build();
        }
        return partService.getPartById(partId)
                .map(PartMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(GET_PART_IMAGE_MAPPING)
    @ResponseBody
    public ResponseEntity<InputStreamResource> getPartImage(@PathVariable Long partId, @PathVariable Long imageId) {
        if (!PartRequestValidator.isValidId(partId) || !PartRequestValidator.isValidId(imageId)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Part> optionalPart = partService.getPartById(partId);
        if (optionalPart.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<PartImage> partImages = optionalPart.get().getPartImages();
        Optional<PartImage> optionalPartImage = partImages.stream()
                .filter(partImage -> partImage.getId().equals(imageId))
                .findFirst();
        if (optionalPartImage.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        PartImage partImage = optionalPartImage.get();
        try {
            InputStream in = partService.getPartImageInputStream(partImage);
            return ResponseEntity.ok()
                    .contentType(MultipartFileUtils.PART_IMAGE_MEDIA_TYPE)
                    .body(new InputStreamResource(in));
        } catch (ImageGetInputStreamException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping()
    public ResponseEntity<PartResponse> createPart(@ModelAttribute PartRequest request) {
        if (!PartRequestValidator.isValidDto(request) || request.getPartImages().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            final Part part = partService.createPart(request);
            final PartResponse partResponse = PartMapper.toDto(part);
            return ResponseEntity.ok(partResponse);
        } catch (EntityAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (ImageSaveException e) {
            return ResponseEntity.internalServerError().build();
        } catch (EntityNotFoundException e) {
            // invalid id passed as one of the fields
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{partId}")
    public ResponseEntity<PartResponse> updatePart(
            @PathVariable Long partId,
            @ModelAttribute PartRequest request
    ) {
        if (!PartRequestValidator.isValidId(partId) || !PartRequestValidator.isValidDto(request)) {
            return ResponseEntity.badRequest().build();
        }
        try {
            return partService.updatePart(partId, request)
                    .map(PartMapper::toDto)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ImageStoreException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{partId}")
    public ResponseEntity<Void> deletePart(@PathVariable Long partId) {
        if (!PartRequestValidator.isValidId(partId)) {
            return ResponseEntity.badRequest().build();
        }
        try {
            if (partService.deletePart(partId)) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (ImageDeleteException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
