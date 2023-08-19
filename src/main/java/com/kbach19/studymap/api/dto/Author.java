package com.kbach19.studymap.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Author {

    private String username;

    private String name;

    private String description;

    private String imageUrl;

}
