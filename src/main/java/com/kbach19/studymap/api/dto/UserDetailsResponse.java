package com.kbach19.studymap.api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class UserDetailsResponse {

    private String username;

    private String email;

    private String firstName;

    private String lastName;

    private String imageUrl;

    private String description;

    private String type;

    private List<Long> boughtCourseIds = new ArrayList<>();

}
