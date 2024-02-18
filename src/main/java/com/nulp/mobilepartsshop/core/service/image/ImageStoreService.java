package com.nulp.mobilepartsshop.core.service.image;

import com.nulp.mobilepartsshop.exception.image.ImageDeleteException;
import com.nulp.mobilepartsshop.exception.image.ImageSaveException;
import org.springframework.web.multipart.MultipartFile;

public interface ImageStoreService {

    String saveImage(MultipartFile image, String filepath) throws ImageSaveException;

    void deleteImage(String filepath) throws ImageDeleteException;
}
