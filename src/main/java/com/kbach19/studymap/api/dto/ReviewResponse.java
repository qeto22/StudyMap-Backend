package com.kbach19.studymap.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewResponse {

    private String reviewText;

    private float rating;

    private Author author;

}
