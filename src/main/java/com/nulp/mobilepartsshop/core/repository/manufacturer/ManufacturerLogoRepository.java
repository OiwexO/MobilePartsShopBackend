package com.nulp.mobilepartsshop.core.repository.manufacturer;

import com.nulp.mobilepartsshop.core.entity.manufacturer.ManufacturerLogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManufacturerLogoRepository extends JpaRepository<ManufacturerLogo, Long> {
}