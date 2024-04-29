package com.nulp.mobilepartsshop.api.v1.user.controller;

import com.nulp.mobilepartsshop.api.v1.ApiConstants;
import com.nulp.mobilepartsshop.api.v1.user.dto.mapper.DeviceMapper;
import com.nulp.mobilepartsshop.api.v1.user.dto.request.DeviceRequest;
import com.nulp.mobilepartsshop.api.v1.user.dto.response.DeviceResponse;
import com.nulp.mobilepartsshop.api.v1.user.dto.validator.DeviceRequestValidator;
import com.nulp.mobilepartsshop.api.v1.user.service.DeviceService;
import com.nulp.mobilepartsshop.core.entity.user.Device;
import com.nulp.mobilepartsshop.exception.entity.EntityAlreadyExistsException;
import com.nulp.mobilepartsshop.exception.entity.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(DeviceController.MAPPING)
@RequiredArgsConstructor
public class DeviceController {

    public static final String MAPPING = ApiConstants.USER_MAPPING_V1 + "/devices";

    private final DeviceService deviceService;

    private final DeviceRequestValidator requestValidator = new DeviceRequestValidator();

    private final DeviceMapper mapper = new DeviceMapper();

    @GetMapping
    public ResponseEntity<List<DeviceResponse>> getAllDevices() {
        List<Device> devices = deviceService.getAllDevices();
        List<DeviceResponse> responseList = mapper.toResponseList(devices);
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{deviceId}")
    public ResponseEntity<DeviceResponse> getDevice(@PathVariable Long deviceId) {
        if (!requestValidator.isValidId(deviceId)) {
            return ResponseEntity.badRequest().build();
        }
        return deviceService.getDeviceById(deviceId)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<DeviceResponse> getDeviceByUserId(@PathVariable Long userId) {
        if (!requestValidator.isValidId(userId)) {
            return ResponseEntity.badRequest().build();
        }
        return deviceService.getDeviceByUserId(userId)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<DeviceResponse> createDevice(
            @PathVariable Long userId,
            @RequestBody DeviceRequest request
    ) {
        if (!requestValidator.isValidId(userId) || !requestValidator.isValidRequest(request)) {
            return ResponseEntity.badRequest().build();
        }
        try {
            final Device device = deviceService.createDevice(userId, request);
            final DeviceResponse response = mapper.toResponse(device);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            // invalid userId
            return ResponseEntity.badRequest().build();
        } catch (EntityAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<DeviceResponse> updateDevice(
            @PathVariable Long userId,
            @RequestBody DeviceRequest request
    ) {
        if (!requestValidator.isValidId(userId) || !requestValidator.isValidRequest(request)) {
            return ResponseEntity.badRequest().build();
        }
        try {
            return deviceService.updateDevice(userId, request)
                    .map(mapper::toResponse)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

//    @DeleteMapping("/{deviceId}")
//    public ResponseEntity<Void> deleteDevice(@PathVariable Long devicesId) {
//        if (!requestValidator.isValidId(devicesId)) {
//            return ResponseEntity.badRequest().build();
//        }
//        if (devicesService.deleteDevice(devicesId)) {
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteDeviceByUserId(@PathVariable Long userId) {
        if (!requestValidator.isValidId(userId)) {
            return ResponseEntity.badRequest().build();
        }
        if (deviceService.deleteDeviceByUserId(userId)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
