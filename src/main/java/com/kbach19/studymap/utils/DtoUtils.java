package com.kbach19.studymap.utils;

import com.kbach19.studymap.api.dto.Author;
import com.kbach19.studymap.api.dto.PostReviewRequest;
import com.kbach19.studymap.model.Review;
import com.kbach19.studymap.model.SystemUser;

public class DtoUtils {

    public static Author toDTO(SystemUser user) {
        return Author.builder()
                .name(user.getFirstName() + " " + user.getLastName())
                .username(user.getUsername())
                .imageUrl(user.getImageUrl())
                .description(user.getDescription())
                .build();
    }

    public static Review getReview(PostReviewRequest request) {
        return Review.builder()
                .comment(request.getComment())
                .rating(request.getRating())
                .author(AuthUtils.getAuthenticatedUser())
                .build();
    }
}
