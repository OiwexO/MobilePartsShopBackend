package com.nulp.mobilepartsshop.api.v1.part.service.manufacturer;

import com.nulp.mobilepartsshop.core.entity.part.manufacturer.Manufacturer;
import com.nulp.mobilepartsshop.core.entity.part.manufacturer.ManufacturerLogo;
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
            MultipartFile logo
    ) throws ImageSaveException;

    ManufacturerLogo updateManufacturerLogo(
            ManufacturerLogo existingLogo,
            MultipartFile newLogoImage
    ) throws ImageStoreException;

    void deleteManufacturerLogo(ManufacturerLogo logo) throws ImageDeleteException;
}
