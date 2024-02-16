package com.nulp.mobilepartsshop.core.service.image;

import com.nulp.mobilepartsshop.exception.image.ImageStoreException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class LocalImageStoreService implements ImageStoreService {

    @Override
    public String saveImage(MultipartFile image, String filepath) throws ImageStoreException {
        final String filename = generateImageFilename(image.getOriginalFilename());
        try {
            final Path targetPath = Paths.get(filepath, filename).toAbsolutePath();
            Files.createDirectories(targetPath.getParent());
            Files.copy(image.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            return targetPath.toString();
        } catch (Exception e) {
            throw new ImageStoreException("Failed to save image: " + e.getMessage(), e.getCause());
        }
    }

    @Override
    public void deleteImage(String filepath) throws IOException {
        final Path targetPath = Paths.get(filepath).toAbsolutePath();
        Files.delete(targetPath);
    }

    private String generateImageFilename(String originalFilename) {
        return System.currentTimeMillis() + "_" + originalFilename;
    }
}
