package com.nulp.mobilepartsshop.core.service.manufacturer;

import com.nulp.mobilepartsshop.api.v1.manufacturer.service.ManufacturerLogoService;
import com.nulp.mobilepartsshop.api.v1.manufacturer.service.ManufacturerService;
import com.nulp.mobilepartsshop.core.entity.manufacturer.ImageType;
import com.nulp.mobilepartsshop.core.entity.manufacturer.Manufacturer;
import com.nulp.mobilepartsshop.core.entity.manufacturer.ManufacturerLogo;
import com.nulp.mobilepartsshop.core.repository.manufacturer.ManufacturerRepository;
import com.nulp.mobilepartsshop.exception.image.ImageDeleteException;
import com.nulp.mobilepartsshop.exception.image.ImageGetInputStreamException;
import com.nulp.mobilepartsshop.exception.image.ImageSaveException;
import com.nulp.mobilepartsshop.exception.image.ImageStoreException;
import com.nulp.mobilepartsshop.exception.manufacturer.ManufacturerNotFoundException;
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
    public Manufacturer createManufacturer(String name, MultipartFile logo, ImageType imageType) throws ImageSaveException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(name);
        final ManufacturerLogo manufacturerLogo = manufacturerLogoService.createManufacturerLogo(manufacturer, logo, imageType);
        manufacturer.setLogo(manufacturerLogo);
        return manufacturerRepository.save(manufacturer);
    }

    @Override
    public Manufacturer updateManufacturer(Long id, String name, MultipartFile newLogoImage, ImageType imageType)
            throws ManufacturerNotFoundException, ImageStoreException {
        Manufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> new ManufacturerNotFoundException("Manufacturer not found with id: " + id));
        manufacturer.setName(name);
        if (!newLogoImage.isEmpty()) {
            manufacturerLogoService.updateManufacturerLogo(manufacturer.getLogo(), newLogoImage, imageType);
        }
        return manufacturerRepository.save(manufacturer);
    }

    @Override
    public void deleteManufacturer(Long id) throws ManufacturerNotFoundException, ImageDeleteException {
        Manufacturer manufacturer = manufacturerRepository.findById(id).orElseThrow(
                () -> new ManufacturerNotFoundException("Manufacturer not found with id: " + id)
        );
        manufacturerLogoService.deleteManufacturerLogo(manufacturer.getLogo());
        manufacturerRepository.deleteById(id);
    }
}
