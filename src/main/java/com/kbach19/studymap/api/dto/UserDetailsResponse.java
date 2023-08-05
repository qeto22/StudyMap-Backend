package com.kbach19.studymap.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDetailsResponse {

    private String username;

    private String email;

    private String firstName;

    private String lastName;

    private String imageUrl;

}
