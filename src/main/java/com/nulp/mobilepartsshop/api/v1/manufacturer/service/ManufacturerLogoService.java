package com.nulp.mobilepartsshop.api.v1.manufacturer.service;

import com.nulp.mobilepartsshop.core.enums.ImageType;
import com.nulp.mobilepartsshop.core.entity.manufacturer.Manufacturer;
import com.nulp.mobilepartsshop.core.entity.manufacturer.ManufacturerLogo;
import com.nulp.mobilepartsshop.exception.image.ImageDeleteException;
import com.nulp.mobilepartsshop.exception.image.ImageGetInputStreamException;
import com.nulp.mobilepartsshop.exception.image.ImageSaveException;
import com.nulp.mobilepartsshop.exception.image.ImageStoreException;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Optional;

public interface ManufacturerLogoService {

    Optional<ManufacturerLogo> getManufacturerLogoById(Long id);

    InputStream getManufacturerLogoInputStream(ManufacturerLogo logo) throws ImageGetInputStreamException;

    ManufacturerLogo createManufacturerLogo(
            Manufacturer manufacturer,
            MultipartFile logo,
            ImageType imageType
    ) throws ImageSaveException;

    ManufacturerLogo updateManufacturerLogo(
            ManufacturerLogo existingLogo,
            MultipartFile newLogoImage,
            ImageType imageType
    ) throws ImageStoreException;

    void deleteManufacturerLogo(ManufacturerLogo logo) throws ImageDeleteException;
}
