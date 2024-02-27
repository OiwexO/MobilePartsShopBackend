package com.nulp.mobilepartsshop.core.service.image;

import com.nulp.mobilepartsshop.exception.image.ImageDeleteException;
import com.nulp.mobilepartsshop.exception.image.ImageGetInputStreamException;
import com.nulp.mobilepartsshop.exception.image.ImageSaveException;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface ImageStoreService {

    InputStream getImageInputStream(String filepath) throws ImageGetInputStreamException;

    String saveImage(MultipartFile image, String filepath) throws ImageSaveException;

    void deleteImage(String filepath) throws ImageDeleteException;
}
