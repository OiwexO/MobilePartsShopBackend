package com.nulp.mobilepartsshop.core.service.user;

import com.nulp.mobilepartsshop.api.v1.part.service.DeviceTypeService;
import com.nulp.mobilepartsshop.api.v1.part.service.manufacturer.ManufacturerService;
import com.nulp.mobilepartsshop.api.v1.user.dto.request.DeviceRequest;
import com.nulp.mobilepartsshop.api.v1.user.service.DeviceService;
import com.nulp.mobilepartsshop.core.entity.part.DeviceType;
import com.nulp.mobilepartsshop.core.entity.part.manufacturer.Manufacturer;
import com.nulp.mobilepartsshop.core.entity.user.Device;
import com.nulp.mobilepartsshop.core.entity.user.User;
import com.nulp.mobilepartsshop.core.repository.user.DeviceRepository;
import com.nulp.mobilepartsshop.core.repository.user.UserRepository;
import com.nulp.mobilepartsshop.exception.entity.EntityAlreadyExistsException;
import com.nulp.mobilepartsshop.exception.entity.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;

    private final UserRepository userRepository;

    private final ManufacturerService manufacturerService;

    private final DeviceTypeService deviceTypeService;

    @Override
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    @Override
    public Optional<Device> getDeviceById(Long id) {
        return deviceRepository.findById(id);
    }

    @Override
    public Optional<Device> getDeviceByUserId(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }
        User user = optionalUser.get();
        Device device = user.getDevice();
        if (device == null) {
            return Optional.empty();
        }
        return Optional.of(device);
    }

    @Override
    public Device createDevice(Long userId, DeviceRequest request) throws EntityAlreadyExistsException, EntityNotFoundException {
        if (deviceRepository.existsByModelAndManufacturerIdAndDeviceTypeId(
                request.getModel(),
                request.getManufacturerId(),
                request.getDeviceTypeId()
        )) {
            throw new EntityAlreadyExistsException();
        }
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException();
        }
        User user = optionalUser.get();
        Device device = new Device();
        setDeviceFields(device, request);
        Device savedDevice = deviceRepository.save(device);
        user.setDevice(savedDevice);
        userRepository.save(user);
        return savedDevice;
    }

    @Override
    public Optional<Device> updateDevice(Long userId, DeviceRequest request) throws EntityNotFoundException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }
        User user = optionalUser.get();
        Device device = user.getDevice();
        if (device == null) {
            return Optional.empty();
        }
        setDeviceFields(device, request);
        Device savedDevice = deviceRepository.save(device);
        user.setDevice(savedDevice);
        userRepository.save(user);
        return Optional.of(savedDevice);
    }

//    @Override
//    public boolean deleteDevice(Long id) {
//        Optional<Device> optionalDevice = deviceRepository.findById(id);
//        if (optionalDevice.isEmpty()) {
//            return false;
//        } //TODO nullify user's device
//        deviceRepository.deleteById(id);
//        return true;
//    }

    @Override
    public boolean deleteDeviceByUserId(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return false;
        }
        User user = optionalUser.get();
        Device device = user.getDevice();
        user.setDevice(null);
        userRepository.save(user);
        deviceRepository.delete(device);
        return true;
    }

    private void setDeviceFields(Device device, DeviceRequest request) throws EntityNotFoundException {
        Manufacturer manufacturer = manufacturerService
                .getManufacturerById(request.getManufacturerId())
                .orElseThrow(EntityNotFoundException::new);
        DeviceType deviceType = deviceTypeService
                .getDeviceTypeById(request.getDeviceTypeId())
                .orElseThrow(EntityNotFoundException::new);
        device.setModel(request.getModel());
        device.setSpecifications(request.getSpecifications());
        device.setManufacturer(manufacturer);
        device.setDeviceType(deviceType);
//        device.setUser(user);
    }
}
