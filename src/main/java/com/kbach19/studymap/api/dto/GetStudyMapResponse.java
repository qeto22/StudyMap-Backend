package com.kbach19.studymap.api.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetStudyMapResponse {

    private String imagePath;

    private String mapTitle;

    private String mapDescription;

    private JsonNode nodeData;

    private Author author;

}
