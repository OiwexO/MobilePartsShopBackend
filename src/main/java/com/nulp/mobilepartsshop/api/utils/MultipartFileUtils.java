package com.nulp.mobilepartsshop.api.utils;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

public class MultipartFileUtils {

    public static final String CONTENT_TYPE_IMAGE = "image/";

    public static final MediaType MANUFACTURER_LOGO_MEDIA_TYPE = MediaType.IMAGE_PNG;

    public static final MediaType PART_IMAGE_MEDIA_TYPE = MediaType.IMAGE_JPEG;

    public static boolean isValidFileForManufacturerLogo(MultipartFile logo) {
        return isValidFile(logo, MANUFACTURER_LOGO_MEDIA_TYPE);
    }

    public static boolean isValidFileForPartImage(MultipartFile partImage) {
        return isValidFile(partImage, PART_IMAGE_MEDIA_TYPE);
    }

    private static boolean isValidFile(MultipartFile file, MediaType expectedMediaType) {
        if (file == null || file.isEmpty()) {
            return false;
        }
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith(CONTENT_TYPE_IMAGE)) {
            return false;
        }

        MediaType mediaType;
        try {
            mediaType = MediaType.parseMediaType(contentType);
        } catch (Exception e) {
            return false;
        }
        return mediaType.isCompatibleWith(expectedMediaType);
    }
}
