package com.kbach19.studymap.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {

    private Long id;

    private String title;

    private String description;

    private String objectives;

    private String tags;

    private String category;

    private BigDecimal price;

    private String imageUrl;

    private Author author;

}
