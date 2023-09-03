package com.kbach19.studymap.api;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/video")
public class VideoController {

    @GetMapping("/{videoFileName}")
    public ResponseEntity<Resource> getVideo(@PathVariable String videoFileName) {
        try {
            String VIDEO_PATH = "/studymap/videos/";
            Path videoPath = Paths.get(VIDEO_PATH, videoFileName);
            Resource resource = new UrlResource(videoPath.toUri());

            if (resource.exists() || resource.isReadable()) {
                String contentType = Files.probeContentType(videoPath);

                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_TYPE, contentType);

                return new ResponseEntity<>(resource, headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
