package com.nulp.mobilepartsshop.api.v1.part.service;

import com.nulp.mobilepartsshop.core.entity.part.Part;
import com.nulp.mobilepartsshop.core.entity.part.PartImage;
import com.nulp.mobilepartsshop.exception.image.ImageDeleteException;
import com.nulp.mobilepartsshop.exception.image.ImageGetInputStreamException;
import com.nulp.mobilepartsshop.exception.image.ImageSaveException;
import com.nulp.mobilepartsshop.exception.image.ImageStoreException;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Optional;

public interface PartImageService {

    Optional<PartImage> getPartImageById(Long id);

    InputStream getPartImageInputStream(PartImage partImage) throws ImageGetInputStreamException;

    PartImage createPartImage(
            Part part,
            MultipartFile partImage
    ) throws ImageSaveException;

    PartImage updatePartImage(
            PartImage existingPartImage,
            MultipartFile newLogoImage
    ) throws ImageStoreException;

    void deletePartImage(PartImage partImage) throws ImageDeleteException;
}
