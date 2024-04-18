package com.nulp.mobilepartsshop.api.v1.part.controller;

import com.nulp.mobilepartsshop.api.v1.ApiConstants;
import com.nulp.mobilepartsshop.api.v1.part.dto.request.PartTypeRequest;
import com.nulp.mobilepartsshop.api.v1.part.dto.response.PartTypeResponse;
import com.nulp.mobilepartsshop.api.v1.part.dto.validator.PartTypeRequestValidator;
import com.nulp.mobilepartsshop.api.v1.part.dto.mapper.PartTypeMapper;
import com.nulp.mobilepartsshop.api.v1.part.service.PartTypeService;
import com.nulp.mobilepartsshop.core.entity.part.PartType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(PartTypeController.MAPPING)
@RequiredArgsConstructor
public class PartTypeController {

    public static final String MAPPING = ApiConstants.PART_MAPPING_V1 + "/part-types";

    private final PartTypeService partTypeService;

    @GetMapping
    public ResponseEntity<List<PartTypeResponse>> getAllPartTypes() {
        List<PartType> partTypes = partTypeService.getAllPartTypes();
        List<PartTypeResponse> partTypeResponses = PartTypeMapper.toDtoList(partTypes);
        return ResponseEntity.ok(partTypeResponses);
    }

    @GetMapping("/{partTypeId}")
    public ResponseEntity<PartTypeResponse> getPartType(@PathVariable Long partTypeId) {
        if (!PartTypeRequestValidator.isValidId(partTypeId)) {
            return ResponseEntity.badRequest().build();
        }
        return partTypeService.getPartTypeById(partTypeId)
                .map(PartTypeMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PartTypeResponse> createPartType(@RequestBody PartTypeRequest partTypeRequest) {
        if (!PartTypeRequestValidator.isValidDto(partTypeRequest)) {
            return ResponseEntity.badRequest().build();
        }
        PartType partType = PartTypeMapper.toEntity(partTypeRequest);
        PartType createdPartType = partTypeService.createPartType(partType);
        PartTypeResponse result = PartTypeMapper.toDto(createdPartType);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{partTypeId}")
    public ResponseEntity<PartTypeResponse> updatePartType(
            @PathVariable Long partTypeId,
            @RequestBody PartTypeRequest partTypeRequest
    ) {
        if (!PartTypeRequestValidator.isValidId(partTypeId) || !PartTypeRequestValidator.isValidDto(partTypeRequest)) {
            return ResponseEntity.badRequest().build();
        }
        PartType partType = PartTypeMapper.toEntity(partTypeRequest);
        return partTypeService.updatePartType(partTypeId, partType)
                .map(PartTypeMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{partTypeId}")
    public ResponseEntity<Void> deletePartType(@PathVariable Long partTypeId) {
        if (!PartTypeRequestValidator.isValidId(partTypeId)) {
            return ResponseEntity.badRequest().build();
        }
        if (partTypeService.deletePartType(partTypeId)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
