package com.kbach19.studymap.api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class MeetingResponse {

    private Long id;

    private Author mentor;

    private Author mentee;

    private Date date;


}
