package com.kbach19.studymap.services;

import com.kbach19.studymap.api.dto.MentorshipRequest;
import com.kbach19.studymap.model.Notification;
import com.kbach19.studymap.model.NotificationType;
import com.kbach19.studymap.model.SystemUser;
import com.kbach19.studymap.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class NotificationService {

    @Autowired
    private SystemUserRepository systemUserRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    public void requestMentorship(MentorshipRequest request) throws Throwable {
        SystemUser mentor = systemUserRepository.findSystemUserByUsername(request.getMentorUsername())
                .orElseThrow((Supplier<Throwable>) () -> new IllegalStateException("Mentor with username " + request.getMentorUsername() + " not found"));

        Notification notification = Notification.builder()
                .message(request.getMessage())
                .type(NotificationType.MENTORSHIP_REQUEST)
                .user(mentor)
                .sender(AuthUtils.getAuthenticatedUser())
                .additionalData(request.getSessionCount().toString())
                .build();
        notificationRepository.save(notification);
    }

}
