package com.nulp.mobilepartsshop.core.service.image;

import com.nulp.mobilepartsshop.exception.image.ImageDeleteException;
import com.nulp.mobilepartsshop.exception.image.ImageGetInputStreamException;
import com.nulp.mobilepartsshop.exception.image.ImageSaveException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class LocalImageStoreService implements ImageStoreService {

    @Override
    public InputStream getImageInputStream(String filepath) throws ImageGetInputStreamException {
        try {
            final Path targetPath = Paths.get(filepath).toAbsolutePath();
            return Files.newInputStream(targetPath);
        } catch (Exception e) {
            throw new ImageGetInputStreamException(e);
        }

    }

    @Override
    public String saveImage(MultipartFile image, String filepath) throws ImageSaveException {
        final String filename = generateImageFilename(image.getOriginalFilename());
        try (InputStream inputStream = image.getInputStream()) {
            final Path targetPath = Paths.get(filepath + File.separator + filename).toAbsolutePath();
            Files.createDirectories(targetPath.getParent());
            Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
            return targetPath.toString();
        } catch (Exception e) {
            throw new ImageSaveException(e);
        }
    }

    @Override
    public void deleteImage(String filepath) throws ImageDeleteException {
        try {
            final Path targetPath = Paths.get(filepath).toAbsolutePath();
            Files.delete(targetPath);
        } catch (IOException e) {
            throw new ImageDeleteException(e);
        }
    }

    private String generateImageFilename(String originalFilename) {
        return System.currentTimeMillis() + File.separatorChar + originalFilename;
    }
}
