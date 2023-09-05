package com.kbach19.studymap.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcceptMentorshipRequest {

    private Long notificationId;

    private List<String> meetingDates;

    private String message;

}
