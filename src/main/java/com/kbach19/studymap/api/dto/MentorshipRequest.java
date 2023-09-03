package com.kbach19.studymap.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MentorshipRequest {

    private Long sessionCount;

    private String message;

    private String mentorUsername;

}
