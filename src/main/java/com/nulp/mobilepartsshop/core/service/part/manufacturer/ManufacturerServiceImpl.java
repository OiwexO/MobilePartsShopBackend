package com.nulp.mobilepartsshop.core.service.part.manufacturer;

import com.nulp.mobilepartsshop.api.v1.part.service.manufacturer.ManufacturerLogoService;
import com.nulp.mobilepartsshop.api.v1.part.service.manufacturer.ManufacturerService;
import com.nulp.mobilepartsshop.core.entity.part.manufacturer.Manufacturer;
import com.nulp.mobilepartsshop.core.entity.part.manufacturer.ManufacturerLogo;
import com.nulp.mobilepartsshop.core.enums.ImageType;
import com.nulp.mobilepartsshop.core.repository.part.manufacturer.ManufacturerRepository;
import com.nulp.mobilepartsshop.exception.image.ImageDeleteException;
import com.nulp.mobilepartsshop.exception.image.ImageGetInputStreamException;
import com.nulp.mobilepartsshop.exception.image.ImageSaveException;
import com.nulp.mobilepartsshop.exception.image.ImageStoreException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManufacturerServiceImpl implements ManufacturerService {

    private final ManufacturerLogoService manufacturerLogoService;

    private final ManufacturerRepository manufacturerRepository;

    @Override
    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    @Override
    public Optional<Manufacturer> getManufacturerById(Long id) {
        return manufacturerRepository.findById(id);
    }

    @Override
    public InputStream getManufacturerLogoInputStream(ManufacturerLogo logo) throws ImageGetInputStreamException {
        return manufacturerLogoService.getManufacturerLogoInputStream(logo);
    }

    @Override
    public Manufacturer createManufacturer(
            String name,
            MultipartFile logo,
            ImageType imageType
    ) throws ImageSaveException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(name);
        ManufacturerLogo manufacturerLogo = manufacturerLogoService.createManufacturerLogo(manufacturer, logo, imageType);
        manufacturer.setLogo(manufacturerLogo);
        return manufacturerRepository.save(manufacturer);
    }

    @Override
    public Optional<Manufacturer> updateManufacturer(
            Long id,
            String name,
            MultipartFile newLogoImage,
            ImageType imageType
    ) throws ImageStoreException {
        Optional<Manufacturer> optionalManufacturer = manufacturerRepository.findById(id);
        if (optionalManufacturer.isEmpty()) {
            return optionalManufacturer;
        }
        Manufacturer manufacturer = optionalManufacturer.get();
        manufacturer.setName(name);
        if (!newLogoImage.isEmpty()) {
            manufacturerLogoService.updateManufacturerLogo(manufacturer.getLogo(), newLogoImage, imageType);
        }
        return Optional.of(manufacturerRepository.save(manufacturer));
    }

    @Override
    public boolean deleteManufacturer(Long id) throws ImageDeleteException {
        Optional<Manufacturer> optionalManufacturer = manufacturerRepository.findById(id);
        if (optionalManufacturer.isEmpty()) {
            return false;
        }
        manufacturerLogoService.deleteManufacturerLogo(optionalManufacturer.get().getLogo());
        manufacturerRepository.deleteById(id);
        return true;
    }
}
