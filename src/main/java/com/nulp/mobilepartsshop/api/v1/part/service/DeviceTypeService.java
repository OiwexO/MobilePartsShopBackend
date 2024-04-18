package com.nulp.mobilepartsshop.api.v1.part.service;

import com.nulp.mobilepartsshop.core.entity.part.DeviceType;

import java.util.List;
import java.util.Optional;

public interface DeviceTypeService {

    List<DeviceType> getAllDeviceTypes();

    Optional<DeviceType> getDeviceTypeById(Long id);

    DeviceType createDeviceType(DeviceType deviceType);

    Optional<DeviceType> updateDeviceType(Long id, DeviceType deviceType);

    boolean deleteDeviceType(Long id);
}
