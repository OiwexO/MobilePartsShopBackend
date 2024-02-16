package com.nulp.mobilepartsshop.api.v1.manufacturer.service;

import com.nulp.mobilepartsshop.api.v1.manufacturer.dto.request.CreateManufacturerRequest;
import com.nulp.mobilepartsshop.core.entity.manufacturer.Manufacturer;
import com.nulp.mobilepartsshop.exception.manufacturer.ManufacturerLogoStoreException;
import com.nulp.mobilepartsshop.exception.manufacturer.ManufacturerNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ManufacturerService {

    List<Manufacturer> getAllManufacturers();

    Optional<Manufacturer> getManufacturerById(Long id);

    Manufacturer createManufacturer(CreateManufacturerRequest request) throws ManufacturerLogoStoreException;

    Manufacturer updateManufacturer(Long id, String name, MultipartFile logo)
            throws ManufacturerNotFoundException, ManufacturerLogoStoreException;

    void deleteManufacturer(Long id) throws ManufacturerNotFoundException;
}

