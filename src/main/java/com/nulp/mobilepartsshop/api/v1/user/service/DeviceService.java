package com.nulp.mobilepartsshop.api.v1.user.service;

import com.nulp.mobilepartsshop.api.v1.user.dto.request.DeviceRequest;
import com.nulp.mobilepartsshop.core.entity.user.Device;
import com.nulp.mobilepartsshop.exception.entity.EntityAlreadyExistsException;
import com.nulp.mobilepartsshop.exception.entity.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public interface DeviceService {

    List<Device> getAllDevices();

    Optional<Device> getDeviceById(Long id);

    Optional<Device> getDeviceByUserId(Long userId);

    Device createDevice(Long userId, DeviceRequest request) throws EntityAlreadyExistsException, EntityNotFoundException;

    Optional<Device> updateDevice(Long userId, DeviceRequest request) throws EntityNotFoundException;

    boolean deleteDeviceByUserId(Long userId);
}
