package com.kbach19.studymap.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kbach19.studymap.api.dto.CreateStudyMapRequest;
import com.kbach19.studymap.api.dto.GetStudyMapResponse;
import com.kbach19.studymap.api.dto.PostReviewRequest;
import com.kbach19.studymap.model.Review;
import com.kbach19.studymap.model.StudyMap;
import com.kbach19.studymap.model.SystemUser;
import com.kbach19.studymap.utils.AuthUtils;
import com.kbach19.studymap.utils.DtoUtils;
import com.kbach19.studymap.utils.JsonUtils;
import com.kbach19.studymap.utils.ValidationUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class StudyMapService {

    @Autowired
    private StudyMapRepository studyMapRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MediaServices mediaServices;

    @SneakyThrows
    public Long create(CreateStudyMapRequest request, MultipartFile image) {
        String imageName = mediaServices.saveImage(image);

        SystemUser user = AuthUtils.getAuthenticatedUser();

        String mapData = JsonUtils.getJsonString(request.getNodeData());

        StudyMap studyMap = StudyMap.builder()
                .author(user)
                .imagePath("/image/" + imageName)
                .title(request.getMapTitle())
                .description(request.getMapDescription())
                .mapData(mapData)
                .build();
        studyMap = studyMapRepository.save(studyMap);
        return studyMap.getId();
    }

    public StudyMap getStudyMap(Long id) {
        return studyMapRepository.findById(id).orElse(null);
    }

    public List<GetStudyMapResponse> getOwnCreatedStudyMaps() {
        Long id = AuthUtils.getAuthenticatedUser().getId();

        return studyMapRepository.findByAuthorId(id).stream()
                .map(studyMap -> {
                    try {
                        return toDto(studyMap);
                    } catch (JsonProcessingException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<GetStudyMapResponse> getPopularStudyMaps() {
        return studyMapRepository.findTop4ByOrderByIdDesc().stream()
                .map(studyMap -> {
                    try {
                        return toDto(studyMap);
                    } catch (JsonProcessingException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private GetStudyMapResponse toDto(StudyMap studyMap) throws JsonProcessingException {
        return GetStudyMapResponse.builder()
                .mapId(studyMap.getId())
                .imagePath(studyMap.getImagePath())
                .mapTitle(studyMap.getTitle())
                .mapDescription(studyMap.getDescription())
                .nodeData(JsonUtils.getJsonNode(studyMap.getMapData()))
                .author(DtoUtils.toDTO(studyMap.getAuthor()))
                .reviews(studyMap.getReviews().stream()
                                 .map(DtoUtils::getPostReviewResponse)
                                 .collect(Collectors.toList()))
                .build();
    }

    public Review postReview(PostReviewRequest request, Long studyMapId) {
        StudyMap studyMap = studyMapRepository.findById(studyMapId)
                .orElseThrow(() -> new IllegalArgumentException("Unknown Study Map Id"));

        if (ValidationUtils.hasPostedReview(studyMap.getReviews())) {
            throw new IllegalArgumentException("You have already posted a review for this study map");
        }

        Review review = DtoUtils.getReview(request);
        review = reviewRepository.save(review);

        studyMap.getReviews().add(review);
        studyMapRepository.save(studyMap);
        return review;
    }
}
