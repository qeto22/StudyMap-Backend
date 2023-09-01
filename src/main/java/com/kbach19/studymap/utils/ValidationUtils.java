package com.kbach19.studymap.utils;

import com.kbach19.studymap.model.Review;

import java.util.List;

public class ValidationUtils {

    public static boolean hasPostedReview(List<Review> reviews) {
        Long currentUserId = AuthUtils.getAuthenticatedUser().getId();

        for (Review review : reviews) {
            Long reviewerId = review.getAuthor().getId();
            if (reviewerId.equals(currentUserId)) {
                return true;
            }
        }

        return false;
    }

}
