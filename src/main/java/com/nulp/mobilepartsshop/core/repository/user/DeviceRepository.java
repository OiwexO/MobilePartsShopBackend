package com.nulp.mobilepartsshop.core.repository.user;

import com.nulp.mobilepartsshop.core.entity.user.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    boolean existsByModelAndManufacturerIdAndDeviceTypeId(
            String model,
            Long manufacturerId,
            Long deviceTypeId
    );
}
