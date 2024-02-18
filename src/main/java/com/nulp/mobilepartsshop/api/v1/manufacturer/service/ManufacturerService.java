package com.nulp.mobilepartsshop.api.v1.manufacturer.service;

import com.nulp.mobilepartsshop.core.entity.manufacturer.ImageType;
import com.nulp.mobilepartsshop.core.entity.manufacturer.Manufacturer;
import com.nulp.mobilepartsshop.exception.image.ImageDeleteException;
import com.nulp.mobilepartsshop.exception.image.ImageSaveException;
import com.nulp.mobilepartsshop.exception.image.ImageStoreException;
import com.nulp.mobilepartsshop.exception.manufacturer.ManufacturerNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ManufacturerService {

    List<Manufacturer> getAllManufacturers();

    Optional<Manufacturer> getManufacturerById(Long id);

    Manufacturer createManufacturer(String name, MultipartFile logo, ImageType imageType) throws ImageSaveException;

    Manufacturer updateManufacturer(Long id, String name, MultipartFile logo, ImageType imageType)
            throws ManufacturerNotFoundException, ImageStoreException;

    void deleteManufacturer(Long id) throws ManufacturerNotFoundException, ImageDeleteException;
}

