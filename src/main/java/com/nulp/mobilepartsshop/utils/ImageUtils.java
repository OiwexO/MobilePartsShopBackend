package com.nulp.mobilepartsshop.utils;

import com.nulp.mobilepartsshop.core.enums.ImageType;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

public class ImageUtils {

    public static ImageType getImageType(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return ImageType.UNSUPPORTED;
        }
        String contentType = file.getContentType();
        if (contentType == null) {
            return ImageType.UNSUPPORTED;
        }
        try {
            MediaType mediaType = MediaType.parseMediaType(file.getContentType());
            if (mediaType.equals(MediaType.IMAGE_JPEG)) {
                return ImageType.JPG;
            } else if (mediaType.equals(MediaType.IMAGE_PNG)) {
                return ImageType.PNG;
            } else {
                return ImageType.UNSUPPORTED;
            }
        } catch (InvalidMediaTypeException e) {
            return ImageType.UNSUPPORTED;
        }
    }
}
