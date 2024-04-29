package com.nulp.mobilepartsshop.core.service.part;

import com.nulp.mobilepartsshop.api.v1.part.service.PartImageService;
import com.nulp.mobilepartsshop.core.entity.part.Part;
import com.nulp.mobilepartsshop.core.entity.part.PartImage;
import com.nulp.mobilepartsshop.core.repository.part.PartImageRepository;
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
public class PartImageServiceImpl implements PartImageService {

    @Value("${upload.part-images-directory}")
    private String PART_IMAGES_DIRECTORY;

    private final ImageStoreService imageStoreService;

    private final PartImageRepository partImageRepository;

    @Override
    public Optional<PartImage> getPartImageById(Long id) {
        return partImageRepository.findById(id);
    }

    @Override
    public InputStream getPartImageInputStream(PartImage partImage) throws ImageGetInputStreamException {
        return imageStoreService.getImageInputStream(partImage.getFilepath());
    }

    @Override
    public PartImage createPartImage(
            MultipartFile partImage
    ) throws ImageSaveException {
        final String filepath = imageStoreService.saveImage(partImage, PART_IMAGES_DIRECTORY);
        final PartImage savedPartImage = PartImage.builder()
                .filepath(filepath)
                .build();
        return partImageRepository.save(savedPartImage);
    }

    @Override
    public PartImage updatePartImage(
            PartImage existingPartImage,
            MultipartFile newPartImage
    ) throws ImageStoreException {
        imageStoreService.deleteImage(existingPartImage.getFilepath());
        final String newPartImagePath = imageStoreService.saveImage(newPartImage, PART_IMAGES_DIRECTORY);
        existingPartImage.setFilepath(newPartImagePath);
        return partImageRepository.save(existingPartImage);
    }

    @Override
    public void deletePartImage(PartImage partImage) throws ImageDeleteException {
        imageStoreService.deleteImage(partImage.getFilepath());
        partImageRepository.delete(partImage);
    }
}
