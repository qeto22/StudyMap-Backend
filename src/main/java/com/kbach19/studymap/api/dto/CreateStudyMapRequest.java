package com.kbach19.studymap.api.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateStudyMapRequest {

    private String mapTitle;

    private String mapDescription;

    private JsonNode nodeData;

}
