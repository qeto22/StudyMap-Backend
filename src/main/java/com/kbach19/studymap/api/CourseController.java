package com.kbach19.studymap.api;


import com.kbach19.studymap.api.dto.*;
import com.kbach19.studymap.model.Review;
import com.kbach19.studymap.model.SystemUser;
import com.kbach19.studymap.model.SystemUserType;
import com.kbach19.studymap.services.CourseService;
import com.kbach19.studymap.utils.AuthUtils;
import com.kbach19.studymap.utils.DtoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/")
    public ResponseEntity<List<CourseResponse>> getCourses() {
        SystemUser systemUser = AuthUtils.getAuthenticatedUser();
        if (systemUser.getType() == SystemUserType.MENTOR) {
            return ResponseEntity.ok(courseService.getAuthoredCourses());
        }
        return ResponseEntity.ok(courseService.getBoughtCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> getCourse(@PathVariable("id") Long id) {
        return ResponseEntity.ok(courseService.getCourse(id));
    }

    @GetMapping("/author/{username}")
    public ResponseEntity<List<CourseResponse>> getAuthorCourses(@PathVariable("username") String username) {
        return ResponseEntity.ok(courseService.getAuthorCourses(username));
    }

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

    @PostMapping(value = "/{id}/review")
    public ResponseEntity<ReviewResponse> postReview(@RequestBody PostReviewRequest request, @PathVariable("id") Long id) {
        Review postedReview = courseService.postReview(request, id);
        return ResponseEntity.ok(DtoUtils.getPostReviewResponse(postedReview));
    }

}
