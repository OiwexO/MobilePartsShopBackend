package com.nulp.mobilepartsshop.api.v1.part.controller;

import com.nulp.mobilepartsshop.api.v1.ApiConstants;
import com.nulp.mobilepartsshop.api.v1.part.dto.mapper.DeviceTypeMapper;
import com.nulp.mobilepartsshop.api.v1.part.dto.request.DeviceTypeRequest;
import com.nulp.mobilepartsshop.api.v1.part.dto.response.DeviceTypeResponse;
import com.nulp.mobilepartsshop.api.v1.part.dto.validator.DeviceTypeRequestValidator;
import com.nulp.mobilepartsshop.api.v1.part.service.DeviceTypeService;
import com.nulp.mobilepartsshop.core.entity.part.DeviceType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(DeviceTypeController.MAPPING)
@RequiredArgsConstructor
public class DeviceTypeController {

    public static final String MAPPING = ApiConstants.PART_MAPPING_V1 + "/device-types";

    private final DeviceTypeService deviceTypeService;

    private final DeviceTypeRequestValidator requestValidator = new DeviceTypeRequestValidator();

    private final DeviceTypeMapper mapper = new DeviceTypeMapper();

    @GetMapping
    public ResponseEntity<List<DeviceTypeResponse>> getAllDeviceTypes() {
        List<DeviceType> deviceTypes = deviceTypeService.getAllDeviceTypes();
        List<DeviceTypeResponse> deviceTypeResponses = mapper.toResponseList(deviceTypes);
        return ResponseEntity.ok(deviceTypeResponses);
    }

    @GetMapping("/{deviceTypeId}")
    public ResponseEntity<DeviceTypeResponse> getDeviceType(@PathVariable Long deviceTypeId) {
        if (!requestValidator.isValidId(deviceTypeId)) {
            return ResponseEntity.badRequest().build();
        }
        return deviceTypeService.getDeviceTypeById(deviceTypeId)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DeviceTypeResponse> createDeviceType(@RequestBody DeviceTypeRequest request) {
        if (!requestValidator.isValidRequest(request)) {
            return ResponseEntity.badRequest().build();
        }
        DeviceType deviceType = mapper.toEntity(request);
        DeviceType createdDeviceType = deviceTypeService.createDeviceType(deviceType);
        DeviceTypeResponse result = mapper.toResponse(createdDeviceType);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{deviceTypeId}")
    public ResponseEntity<DeviceTypeResponse> updateDeviceType(
            @PathVariable Long deviceTypeId,
            @RequestBody DeviceTypeRequest request
    ) {
        if (!requestValidator.isValidId(deviceTypeId) || !requestValidator.isValidRequest(request)) {
            return ResponseEntity.badRequest().build();
        }
        DeviceType deviceType = mapper.toEntity(request);
        return deviceTypeService.updateDeviceType(deviceTypeId, deviceType)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{deviceTypeId}")
    public ResponseEntity<Void> deleteDeviceType(@PathVariable Long deviceTypeId) {
        if (!requestValidator.isValidId(deviceTypeId)) {
            return ResponseEntity.badRequest().build();
        }
        if (deviceTypeService.deleteDeviceType(deviceTypeId)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}