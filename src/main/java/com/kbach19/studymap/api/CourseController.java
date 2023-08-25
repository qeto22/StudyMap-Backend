package com.kbach19.studymap.api;


import com.kbach19.studymap.api.dto.CreateCourseRequest;
import com.kbach19.studymap.api.dto.CreateCourseResponse;
import com.kbach19.studymap.api.dto.CreateSectionRequest;
import com.kbach19.studymap.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateCourseResponse> createCourse(@RequestPart("image") MultipartFile image,
                                                               @RequestPart("request") CreateCourseRequest request) {
        return ResponseEntity.ok(CreateCourseResponse.builder()
                .id(courseService.create(request, image))
                .build());
    }

    @PostMapping(value = "/section", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createSection(@RequestPart("videos") MultipartFile[] videos,
                                              @RequestPart("request") CreateSectionRequest request) {
        courseService.createSection(videos, request);
        return ResponseEntity.ok()
                             .build();
    }

}