package com.nulp.mobilepartsshop.core.service.part;

import com.nulp.mobilepartsshop.api.v1.part.dto.request.PartRequest;
import com.nulp.mobilepartsshop.api.v1.part.service.DeviceTypeService;
import com.nulp.mobilepartsshop.api.v1.part.service.PartImageService;
import com.nulp.mobilepartsshop.api.v1.part.service.PartService;
import com.nulp.mobilepartsshop.api.v1.part.service.PartTypeService;
import com.nulp.mobilepartsshop.api.v1.part.service.manufacturer.ManufacturerService;
import com.nulp.mobilepartsshop.core.entity.part.DeviceType;
import com.nulp.mobilepartsshop.core.entity.part.Part;
import com.nulp.mobilepartsshop.core.entity.part.PartImage;
import com.nulp.mobilepartsshop.core.entity.part.PartType;
import com.nulp.mobilepartsshop.core.entity.part.manufacturer.Manufacturer;
import com.nulp.mobilepartsshop.core.repository.part.PartRepository;
import com.nulp.mobilepartsshop.exception.entity.EntityAlreadyExistsException;
import com.nulp.mobilepartsshop.exception.entity.EntityNotFoundException;
import com.nulp.mobilepartsshop.exception.image.ImageDeleteException;
import com.nulp.mobilepartsshop.exception.image.ImageGetInputStreamException;
import com.nulp.mobilepartsshop.exception.image.ImageSaveException;
import com.nulp.mobilepartsshop.exception.image.ImageStoreException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;

    private final PartImageService partImageService;

    private final ManufacturerService manufacturerService;

    private final DeviceTypeService deviceTypeService;

    private final PartTypeService partTypeService;

    @Override
    public List<Part> getAllParts() {
        return partRepository.findAll();
    }

    @Override
    public Optional<Part> getPartById(Long id) {
        return partRepository.findById(id);
    }

    @Override
    public InputStream getPartImageInputStream(PartImage partImage) throws ImageGetInputStreamException {
        return partImageService.getPartImageInputStream(partImage);
    }

    @Override
    public Part createPart(PartRequest partRequest) throws EntityAlreadyExistsException, ImageSaveException, EntityNotFoundException {
        if (partRepository.existsByModelAndManufacturerIdAndDeviceTypeIdAndPartTypeId(
                partRequest.getModel(),
                partRequest.getManufacturerId(),
                partRequest.getDeviceTypeId(),
                partRequest.getPartTypeId()
                )) {
            throw new EntityAlreadyExistsException();
        }
        Part part = new Part();
        List<MultipartFile> images = partRequest.getPartImages();
        List<PartImage> partImages = new ArrayList<>();
        for (MultipartFile image : images) {
            PartImage partImage = partImageService.createPartImage(part, image);
            partImages.add(partImage);
        }
        setPartFields(part, partRequest, partImages);
        return partRepository.save(part);
    }

    @Override
    public Optional<Part> updatePart(Long id, PartRequest partRequest) throws ImageStoreException, EntityNotFoundException {
        Optional<Part> optionalPart = partRepository.findById(id);
        if (optionalPart.isEmpty()) {
            return Optional.empty();
        }
        Part part = optionalPart.get();
        List<PartImage> partImages = part.getPartImages();
        List<MultipartFile> images = partRequest.getPartImages();
        if (images != null && !images.isEmpty()) {
            for (PartImage partImage : partImages) {
                partImageService.deletePartImage(partImage);
                partImages.remove(partImage);
            }
            for (MultipartFile image : images) {
                if (!image.isEmpty()) {
                    PartImage newPartImage = partImageService.createPartImage(part, image);
                    partImages.add(newPartImage);
                }
            }
        }
        setPartFields(part, partRequest, partImages);
        return Optional.of(partRepository.save(part));
    }

    @Override
    public boolean deletePart(Long id) throws ImageDeleteException {
        Optional<Part> optionalPart = partRepository.findById(id);
        if (optionalPart.isEmpty()) {
            return false;
        }
        List<PartImage> partImages = optionalPart.get().getPartImages();
        for (PartImage partImage : partImages) {
            partImageService.deletePartImage(partImage);
        }
        partRepository.deleteById(id);
        return false;
    }

    private void setPartFields(Part part, PartRequest partRequest, List<PartImage> partImages) throws EntityNotFoundException {
        Manufacturer manufacturer = manufacturerService
                .getManufacturerById(partRequest.getManufacturerId())
                .orElseThrow(EntityNotFoundException::new);
        DeviceType deviceType = deviceTypeService
                .getDeviceTypeById(partRequest.getDeviceTypeId())
                .orElseThrow(EntityNotFoundException::new);
        PartType partType = partTypeService
                .getPartTypeById(partRequest.getPartTypeId())
                .orElseThrow(EntityNotFoundException::new);
        part.setPrice(partRequest.getPrice());
        part.setQuantity(partRequest.getQuantity());
        part.setModel(partRequest.getModel());
        part.setSpecifications(partRequest.getSpecifications());
        part.setManufacturer(manufacturer);
        part.setDeviceType(deviceType);
        part.setPartType(partType);
        part.setPartImages(partImages);
    }
}