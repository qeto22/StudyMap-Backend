package com.kbach19.studymap.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCourseRequest {

    private String courseTitle;

    private String courseCategory;

    private BigDecimal coursePrice;

    private String courseDescription;

    private String courseTags;

    private String courseLearningObjectives;

}
