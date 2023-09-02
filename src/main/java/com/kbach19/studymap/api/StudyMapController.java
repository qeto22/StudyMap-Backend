package com.kbach19.studymap.api;

import com.kbach19.studymap.api.dto.CreateStudyMapRequest;
import com.kbach19.studymap.api.dto.CreateStudyMapResponse;
import com.kbach19.studymap.api.dto.GetStudyMapResponse;
import com.kbach19.studymap.api.dto.*;
import com.kbach19.studymap.model.Review;
import com.kbach19.studymap.model.StudyMap;
import com.kbach19.studymap.services.StudyMapService;
import com.kbach19.studymap.utils.DtoUtils;
import com.kbach19.studymap.utils.JsonUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/map")
public class StudyMapController {

    @Autowired
    private StudyMapService studyMapService;

    @SneakyThrows
    @GetMapping("/{id}")
    public ResponseEntity<GetStudyMapResponse> getStudyMap(@PathVariable Long id) {
        StudyMap studyMap = studyMapService.getStudyMap(id);
        if (studyMap == null) {
            return ResponseEntity.badRequest()
                    .build();
        }

        return ResponseEntity.ok(GetStudyMapResponse.builder()
                .mapId(studyMap.getId())
                .imagePath(studyMap.getImagePath())
                .mapTitle(studyMap.getTitle())
                .mapDescription(studyMap.getDescription())
                .nodeData(JsonUtils.getJsonNode(studyMap.getMapData()))
                .author(DtoUtils.toDTO(studyMap.getAuthor()))
                        .reviews(studyMap.getReviews().stream()
                                .map(DtoUtils::getPostReviewResponse)
                                .collect(Collectors.toList()))
                .build());
    }

    @GetMapping("/own-maps")
    public ResponseEntity<List<GetStudyMapResponse>> getOwnCreatedStudyMaps() {
        return ResponseEntity.ok(studyMapService.getOwnCreatedStudyMaps());
    }

    @GetMapping("/author/{username}")
    public ResponseEntity<List<GetStudyMapResponse>> getAuthorCourses(@PathVariable("username") String username) {
        return ResponseEntity.ok(studyMapService.getAuthorMaps(username));
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateStudyMapResponse> createStudyMap(@RequestPart("image") MultipartFile image,
                                                                 @RequestPart("request") CreateStudyMapRequest request) {
        return ResponseEntity.ok(CreateStudyMapResponse.builder()
                .id(studyMapService.create(request, image))
                .build());
    }

    @PostMapping(value = "/{id}/review")
    public ResponseEntity<ReviewResponse> postReview(@RequestBody PostReviewRequest request, @PathVariable("id") Long id) {
        Review postedReview = studyMapService.postReview(request, id);
        return ResponseEntity.ok(DtoUtils.getPostReviewResponse(postedReview));
    }

}
