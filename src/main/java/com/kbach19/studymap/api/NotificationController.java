package com.kbach19.studymap.api;

import com.kbach19.studymap.api.dto.*;
import com.kbach19.studymap.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getNotifications() {
        return ResponseEntity.ok(notificationService.getNotifications());
    }

    @GetMapping("/unread")
    public ResponseEntity<List<NotificationResponse>> getUnreadNotifications() {
        return ResponseEntity.ok(notificationService.getUnreadNotifications());
    }

    @PostMapping
    public ResponseEntity<Void> markNotificationsAsRead(@RequestBody MarkNotificationAsReadRequest request) {
        notificationService.markNotificationsAsRead(request.getNotificationIds());
        return ResponseEntity.ok(null);
    }

    @PostMapping("/request-mentorship")
    public ResponseEntity<Void> requestMentorship(@RequestBody MentorshipRequest request) throws Throwable {
        notificationService.requestMentorship(request);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/accept-mentorship")
    public ResponseEntity<Void> acceptMentorship(@RequestBody AcceptMentorshipRequest request) throws Throwable {
        notificationService.acceptMentorship(request);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/reject-mentorship")
    public ResponseEntity<Void> rejectMentorship(@RequestBody RejectMentorshipRequest request) throws Throwable {
        notificationService.rejectMentorship(request);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/reject-accepted-mentorship")
    public ResponseEntity<Void> rejectMentorship(@RequestBody RejectAcceptedMentorshipRequest request) throws Throwable {
        notificationService.rejectAcceptedMentorship(request);
        return ResponseEntity.ok(null);
    }

}
