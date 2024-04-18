package com.nulp.mobilepartsshop.core.repository.part.manufacturer;

import com.nulp.mobilepartsshop.core.entity.part.manufacturer.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    boolean existsManufacturerByName(String name);
}
