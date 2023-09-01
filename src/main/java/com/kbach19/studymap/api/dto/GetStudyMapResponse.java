package com.kbach19.studymap.api.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class GetStudyMapResponse {

    private Long mapId;

    private String imagePath;

    private String mapTitle;

    private String mapDescription;

    private JsonNode nodeData;

    private Author author;

    private List<ReviewResponse> reviews = new ArrayList<>();

}
