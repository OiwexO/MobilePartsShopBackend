package com.nulp.mobilepartsshop.core.service.image;

import com.nulp.mobilepartsshop.exception.image.ImageStoreException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageStoreService {

    String saveImage(MultipartFile image, String filepath) throws ImageStoreException;

    void deleteImage(String filepath) throws IOException;
}
