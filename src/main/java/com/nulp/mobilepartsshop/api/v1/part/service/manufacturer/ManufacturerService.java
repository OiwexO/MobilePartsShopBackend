package com.nulp.mobilepartsshop.api.v1.part.service.manufacturer;

import com.nulp.mobilepartsshop.core.entity.part.manufacturer.Manufacturer;
import com.nulp.mobilepartsshop.core.entity.part.manufacturer.ManufacturerLogo;
import com.nulp.mobilepartsshop.core.enums.ImageType;
import com.nulp.mobilepartsshop.exception.image.ImageDeleteException;
import com.nulp.mobilepartsshop.exception.image.ImageGetInputStreamException;
import com.nulp.mobilepartsshop.exception.image.ImageSaveException;
import com.nulp.mobilepartsshop.exception.image.ImageStoreException;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface ManufacturerService {

    List<Manufacturer> getAllManufacturers();

    Optional<Manufacturer> getManufacturerById(Long id);

    InputStream getManufacturerLogoInputStream(ManufacturerLogo logo) throws ImageGetInputStreamException;

    Manufacturer createManufacturer(String name, MultipartFile logo, ImageType imageType) throws ImageSaveException;

    Optional<Manufacturer> updateManufacturer(Long id, String name, MultipartFile logo, ImageType imageType)
            throws ImageStoreException;

    boolean deleteManufacturer(Long id) throws ImageDeleteException;
}
