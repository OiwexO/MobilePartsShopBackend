package com.nulp.mobilepartsshop.api.v1.partType.controller;

import com.nulp.mobilepartsshop.api.v1.ApiConstants;
import com.nulp.mobilepartsshop.api.v1.partType.dto.PartTypeDto;
import com.nulp.mobilepartsshop.api.v1.partType.dto.PartTypeDtoValidator;
import com.nulp.mobilepartsshop.api.v1.partType.dto.PartTypeMapper;
import com.nulp.mobilepartsshop.api.v1.partType.service.PartTypeService;
import com.nulp.mobilepartsshop.core.entity.part.PartType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(PartTypeController.MAPPING)
@RequiredArgsConstructor
public class PartTypeController {

    public static final String MAPPING = ApiConstants.GLOBAL_MAPPING_V1 + "/part-types";

    private final PartTypeService partTypeService;

    @GetMapping
    public ResponseEntity<List<PartTypeDto>> getAllPartTypes() {
        List<PartType> partTypes = partTypeService.getAllPartTypes();
        List<PartTypeDto> partTypeDtos = PartTypeMapper.toDtoList(partTypes);
        return ResponseEntity.ok(partTypeDtos);
    }

    @GetMapping("/{partTypeId}")
    public ResponseEntity<PartTypeDto> getPartType(@PathVariable Long partTypeId) {
        if (!PartTypeDtoValidator.isValidId(partTypeId)) {
            return ResponseEntity.badRequest().build();
        }
        return partTypeService.getPartTypeById(partTypeId)
                .map(PartTypeMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PartTypeDto> createPartType(@RequestBody PartTypeDto partTypeDto) {
        if (!PartTypeDtoValidator.isValidDto(partTypeDto)) {
            return ResponseEntity.badRequest().build();
        }
        PartType partType = PartTypeMapper.toEntity(partTypeDto);
        PartType createdPartType = partTypeService.createPartType(partType);
        PartTypeDto result = PartTypeMapper.toDto(createdPartType);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{partTypeId}")
    public ResponseEntity<PartTypeDto> updatePartType(
            @PathVariable Long partTypeId,
            @RequestBody PartTypeDto partTypeDto
    ) {
        if (!PartTypeDtoValidator.isValidId(partTypeId) || !PartTypeDtoValidator.isValidDto(partTypeDto)) {
            return ResponseEntity.badRequest().build();
        }
        PartType partType = PartTypeMapper.toEntity(partTypeDto);
        return partTypeService.updatePartType(partTypeId, partType)
                .map(PartTypeMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{partTypeId}")
    public ResponseEntity<Void> deletePartType(@PathVariable Long partTypeId) {
        if (!PartTypeDtoValidator.isValidId(partTypeId)) {
            return ResponseEntity.badRequest().build();
        }
        if (partTypeService.deletePartType(partTypeId)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
