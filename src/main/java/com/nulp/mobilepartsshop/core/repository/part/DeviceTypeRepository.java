package com.nulp.mobilepartsshop.core.repository.part;

import com.nulp.mobilepartsshop.core.entity.part.DeviceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceTypeRepository extends JpaRepository<DeviceType, Long> {

}
