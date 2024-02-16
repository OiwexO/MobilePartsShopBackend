package com.nulp.mobilepartsshop.api.v1.manufacturer.service;

import com.nulp.mobilepartsshop.core.entity.manufacturer.Manufacturer;
import com.nulp.mobilepartsshop.core.entity.manufacturer.ManufacturerLogo;
import com.nulp.mobilepartsshop.exception.manufacturer.ManufacturerLogoStoreException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ManufacturerLogoService {

    Optional<ManufacturerLogo> getManufacturerLogoById(Long id);

    ManufacturerLogo createManufacturerLogo(Manufacturer manufacturer, MultipartFile logo)
            throws ManufacturerLogoStoreException;

    ManufacturerLogo updateManufacturerLogo(Manufacturer manufacturer, MultipartFile logo)
            throws ManufacturerLogoStoreException;

}
