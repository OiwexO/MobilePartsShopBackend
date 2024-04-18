package com.nulp.mobilepartsshop.core.service.part.manufacturer;

import com.nulp.mobilepartsshop.api.v1.part.service.manufacturer.ManufacturerLogoService;
import com.nulp.mobilepartsshop.core.entity.part.manufacturer.Manufacturer;
import com.nulp.mobilepartsshop.core.entity.part.manufacturer.ManufacturerLogo;
import com.nulp.mobilepartsshop.core.repository.part.manufacturer.ManufacturerLogoRepository;
import com.nulp.mobilepartsshop.core.service.image.ImageStoreService;
import com.nulp.mobilepartsshop.exception.image.ImageDeleteException;
import com.nulp.mobilepartsshop.exception.image.ImageGetInputStreamException;
import com.nulp.mobilepartsshop.exception.image.ImageSaveException;
import com.nulp.mobilepartsshop.exception.image.ImageStoreException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManufacturerLogoServiceImpl implements ManufacturerLogoService {

    @Value("${upload.logos-directory}")
    private String LOGOS_DIRECTORY;

    private final ImageStoreService imageStoreService;

    private final ManufacturerLogoRepository manufacturerLogoRepository;

    @Override
    public Optional<ManufacturerLogo> getManufacturerLogoById(Long id) {
        return manufacturerLogoRepository.findById(id);
    }

    @Override
    public InputStream getManufacturerLogoInputStream(ManufacturerLogo logo) throws ImageGetInputStreamException {
        return imageStoreService.getImageInputStream(logo.getFilepath());
    }

    @Override
    public ManufacturerLogo createManufacturerLogo(
            Manufacturer manufacturer,
            MultipartFile logo
    ) throws ImageSaveException {
        final String filepath = imageStoreService.saveImage(logo, LOGOS_DIRECTORY);
        final ManufacturerLogo manufacturerLogo = ManufacturerLogo.builder()
                .filepath(filepath)
                .manufacturer(manufacturer)
                .build();
        return manufacturerLogoRepository.save(manufacturerLogo);
    }

    @Override
    public ManufacturerLogo updateManufacturerLogo(
            ManufacturerLogo existingLogo,
            MultipartFile newLogoImage
    ) throws ImageStoreException {
        imageStoreService.deleteImage(existingLogo.getFilepath());
        final String newLogoPath = imageStoreService.saveImage(newLogoImage, LOGOS_DIRECTORY);
        existingLogo.setFilepath(newLogoPath);
        return manufacturerLogoRepository.save(existingLogo);
    }

    @Override
    public void deleteManufacturerLogo(ManufacturerLogo logo) throws ImageDeleteException {
        imageStoreService.deleteImage(logo.getFilepath());
        manufacturerLogoRepository.delete(logo);
    }
}
