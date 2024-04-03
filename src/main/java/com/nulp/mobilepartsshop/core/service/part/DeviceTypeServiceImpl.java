package com.nulp.mobilepartsshop.core.service.part;

import com.nulp.mobilepartsshop.api.v1.part.service.DeviceTypeService;
import com.nulp.mobilepartsshop.core.entity.part.DeviceType;
import com.nulp.mobilepartsshop.core.repository.part.DeviceTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceTypeServiceImpl implements DeviceTypeService {

    private final DeviceTypeRepository deviceTypeRepository;

    @Override
    public List<DeviceType> getAllDeviceTypes() {
        return deviceTypeRepository.findAll();
    }

    @Override
    public Optional<DeviceType> getDeviceTypeById(Long id) {
        return deviceTypeRepository.findById(id);
    }

    @Override
    public DeviceType createDeviceType(DeviceType deviceType) {
        return deviceTypeRepository.save(deviceType);
    }

    @Override
    public Optional<DeviceType> updateDeviceType(Long id, DeviceType deviceType) {
        return deviceTypeRepository.findById(id)
                .map(existingDeviceType -> {
                    deviceType.setId(id);
                    return deviceTypeRepository.save(deviceType);
                });
    }

    @Override
    public boolean deleteDeviceType(Long id) {
        if (deviceTypeRepository.existsById(id)) {
            deviceTypeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
