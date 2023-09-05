package com.kbach19.studymap.api;

import com.kbach19.studymap.api.dto.MeetingResponse;
import com.kbach19.studymap.api.dto.VideoCallTokenResponse;
import com.kbach19.studymap.model.Meeting;
import com.kbach19.studymap.model.SystemUser;
import com.kbach19.studymap.services.MeetingRepository;
import com.kbach19.studymap.utils.AuthUtils;
import com.kbach19.studymap.utils.DtoUtils;
import com.twilio.jwt.accesstoken.AccessToken;
import com.twilio.jwt.accesstoken.VideoGrant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/video")
public class VideoCallController {

    @Value("${twilio.accountSid}")
    private String accountSid;


    @Value("${twilio.apiKeySid}")
    private String apiKeySid;


    @Value("${twilio.apiKeySecret}")
    private String apiKeySecret;

    @Autowired
    private MeetingRepository meetingRepository;

    @GetMapping("/token")
    public ResponseEntity<VideoCallTokenResponse> getToken() {
        VideoGrant videoGrant = new VideoGrant();
        videoGrant.setRoom("TestRoom");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SystemUser systemUser = (SystemUser) authentication.getPrincipal();

        AccessToken token = new AccessToken.Builder(accountSid, apiKeySid, apiKeySecret)
                .identity(systemUser.getUsername())
                .grant(videoGrant)
                .build();

        return ResponseEntity.ok(VideoCallTokenResponse.builder()
                .token(token.toJwt())
                .roomName("TestRoom") // TODO: 8/17/2023 Generate Room Names 
                .build());
    }

    @GetMapping("/meetings")
    public ResponseEntity<List<MeetingResponse>> getMeetings() {
        Long currUserId = AuthUtils.getAuthenticatedUser().getId();

        List<MeetingResponse> meetings = meetingRepository.findByMentorIdOrMenteeId(currUserId, currUserId)
                .stream()
                .map(meeting -> MeetingResponse.builder()
                        .id(meeting.getId())
                        .date(meeting.getDate())
                        .mentee(DtoUtils.toDTO(meeting.getMentee()))
                        .mentor(DtoUtils.toDTO(meeting.getMentor()))
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(meetings);
    }
}