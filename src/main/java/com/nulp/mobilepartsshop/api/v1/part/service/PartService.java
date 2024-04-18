package com.nulp.mobilepartsshop.api.v1.part.service;

import com.nulp.mobilepartsshop.api.v1.part.dto.request.PartRequest;
import com.nulp.mobilepartsshop.core.entity.part.Part;
import com.nulp.mobilepartsshop.core.entity.part.PartImage;
import com.nulp.mobilepartsshop.exception.entity.EntityAlreadyExistsException;
import com.nulp.mobilepartsshop.exception.entity.EntityNotFoundException;
import com.nulp.mobilepartsshop.exception.image.ImageDeleteException;
import com.nulp.mobilepartsshop.exception.image.ImageGetInputStreamException;
import com.nulp.mobilepartsshop.exception.image.ImageSaveException;
import com.nulp.mobilepartsshop.exception.image.ImageStoreException;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface PartService {

    List<Part> getAllParts();

    Optional<Part> getPartById(Long id);

    InputStream getPartImageInputStream(PartImage partImage) throws ImageGetInputStreamException;

    Part createPart(PartRequest partRequest) throws EntityAlreadyExistsException, ImageSaveException, EntityNotFoundException;

    Optional<Part> updatePart(Long id, PartRequest partRequest)
            throws ImageStoreException, EntityNotFoundException;

    boolean deletePart(Long id) throws ImageDeleteException;
}
