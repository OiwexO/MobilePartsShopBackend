package com.nulp.mobilepartsshop.core.service.manufacturer;

import com.nulp.mobilepartsshop.api.v1.manufacturer.service.ManufacturerLogoService;
import com.nulp.mobilepartsshop.core.entity.manufacturer.Manufacturer;
import com.nulp.mobilepartsshop.core.entity.manufacturer.ManufacturerLogo;
import com.nulp.mobilepartsshop.core.repository.manufacturer.ManufacturerLogoRepository;
import com.nulp.mobilepartsshop.core.service.image.ImageStoreService;
import com.nulp.mobilepartsshop.exception.image.ImageStoreException;
import com.nulp.mobilepartsshop.exception.manufacturer.ManufacturerLogoStoreException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public ManufacturerLogo createManufacturerLogo(Manufacturer manufacturer, MultipartFile logo) throws ManufacturerLogoStoreException {
        final String filepath;
        try {
            filepath = imageStoreService.saveImage(logo, LOGOS_DIRECTORY);
        } catch (ImageStoreException e) {
            throw new ManufacturerLogoStoreException(e.getMessage(), e.getCause());
        }
        final ManufacturerLogo manufacturerLogo = ManufacturerLogo.builder()
                .filepath(filepath)
                .manufacturer(manufacturer)
                .build();
        return manufacturerLogoRepository.save(manufacturerLogo);
    }

    @Override
    public ManufacturerLogo updateManufacturerLogo(Manufacturer manufacturer, MultipartFile logo)
            throws ManufacturerLogoStoreException {
        ManufacturerLogo existingLogo = manufacturer.getLogo();
        try {
            imageStoreService.deleteImage(existingLogo.getFilepath());
        } catch (IOException e) {
            throw new ManufacturerLogoStoreException(e.getMessage(), e.getCause());
        }
        final String newLogoPath;
        try {
            newLogoPath = imageStoreService.saveImage(logo, LOGOS_DIRECTORY);
        } catch (ImageStoreException e) {
            throw new ManufacturerLogoStoreException(e.getMessage(), e.getCause());
        }
        existingLogo.setFilepath(newLogoPath);
        return manufacturerLogoRepository.save(existingLogo);
    }
}
