package com.nulp.mobilepartsshop.core.repository.part;

import com.nulp.mobilepartsshop.core.entity.part.Part;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartRepository extends JpaRepository<Part, Long> {

    boolean existsByNameAndManufacturerIdAndDeviceTypeIdAndPartTypeId(
            String name,
            Long manufacturerId,
            Long deviceTypeId,
            Long partTypeId
    );
}
