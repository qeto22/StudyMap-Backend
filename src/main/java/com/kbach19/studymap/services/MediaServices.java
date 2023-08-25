package com.kbach19.studymap.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class MediaServices {

    public String saveImage(MultipartFile imageFile) {
        String folder = "/studymap/images/";
        return saveMedia(imageFile, folder);
    }

    public String saveVideo(MultipartFile videoFile) {
        String folder = "/studymap/videos/";
        return saveMedia(videoFile, folder);
    }

    private String saveMedia(MultipartFile multipartFile, String folderPath) {
        try {
            String fileName = UUID.randomUUID().toString();

            byte[] bytes = multipartFile.getBytes();
            Path path = Paths.get(folderPath + fileName + multipartFile.getOriginalFilename());
            Files.createFile(path);
            Files.write(path, bytes);

            return fileName + multipartFile.getOriginalFilename();
        } catch (IOException e) {
            throw new RuntimeException("Failed to store media content", e);
        }
    }
}
