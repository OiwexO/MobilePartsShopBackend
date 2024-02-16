package com.nulp.mobilepartsshop.core.service.manufacturer;

import com.nulp.mobilepartsshop.api.v1.manufacturer.dto.request.CreateManufacturerRequest;
import com.nulp.mobilepartsshop.api.v1.manufacturer.service.ManufacturerLogoService;
import com.nulp.mobilepartsshop.api.v1.manufacturer.service.ManufacturerService;
import com.nulp.mobilepartsshop.core.entity.manufacturer.Manufacturer;
import com.nulp.mobilepartsshop.core.entity.manufacturer.ManufacturerLogo;
import com.nulp.mobilepartsshop.core.repository.manufacturer.ManufacturerRepository;
import com.nulp.mobilepartsshop.exception.manufacturer.ManufacturerLogoStoreException;
import com.nulp.mobilepartsshop.exception.manufacturer.ManufacturerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public Manufacturer createManufacturer(CreateManufacturerRequest request) throws ManufacturerLogoStoreException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(request.getName());
        final ManufacturerLogo logo = manufacturerLogoService.createManufacturerLogo(manufacturer, request.getLogo());
        manufacturer.setLogo(logo);
        return manufacturerRepository.save(manufacturer);
    }

    @Override
    public Manufacturer updateManufacturer(Long id, String name, MultipartFile logo)
            throws ManufacturerNotFoundException, ManufacturerLogoStoreException {
        Manufacturer existingManufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> new ManufacturerNotFoundException("Manufacturer not found with id: " + id));
        existingManufacturer.setName(name);
        if (!logo.isEmpty()) {
            manufacturerLogoService.updateManufacturerLogo(existingManufacturer, logo);
        }
        return manufacturerRepository.save(existingManufacturer);
    }

    @Override
    public void deleteManufacturer(Long id) throws ManufacturerNotFoundException {
        if (manufacturerRepository.findById(id).isPresent()) {
            manufacturerRepository.deleteById(id);
        } else {
            throw new ManufacturerNotFoundException("Manufacturer not found with id: " + id);
        }
    }
}
