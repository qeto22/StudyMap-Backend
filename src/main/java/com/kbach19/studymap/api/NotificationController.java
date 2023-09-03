package com.kbach19.studymap.api;

import com.kbach19.studymap.api.dto.MentorshipRequest;
import com.kbach19.studymap.api.dto.MentorshipRequestResponse;
import com.kbach19.studymap.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/request-mentorship")
    public ResponseEntity<Void> requestMentorship(@RequestBody MentorshipRequest request) throws Throwable {
        notificationService.requestMentorship(request);
        return ResponseEntity.ok(null);
    }

}
