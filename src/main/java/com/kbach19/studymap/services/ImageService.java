package com.kbach19.studymap.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageService {

    public String saveImage(MultipartFile imageFile) {
        try {
            String fileName = UUID.randomUUID().toString();

            String folder = "/studymap/images/";
            byte[] bytes = imageFile.getBytes();
            Path path = Paths.get(folder + fileName + imageFile.getOriginalFilename());
            Files.createFile(path);
            Files.write(path, bytes);

            return fileName + imageFile.getOriginalFilename();
        } catch (IOException e) {
            throw new RuntimeException("Failed to store image", e);
        }
    }

}
