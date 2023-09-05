package com.kbach19.studymap.services;

import com.kbach19.studymap.api.dto.MentorshipRequest;
import com.kbach19.studymap.model.Notification;
import com.kbach19.studymap.model.NotificationType;
import com.kbach19.studymap.model.SystemUser;
import com.kbach19.studymap.api.dto.NotificationResponse;
import com.kbach19.studymap.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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

    public List<NotificationResponse> getNotifications() {
        Long userId = AuthUtils.getAuthenticatedUser().getId();

        List<Notification> notificationsList = notificationRepository.findAllByUserId(userId);
        notificationsList.forEach(notification -> notification.setSeenByUser(true));
        notificationRepository.saveAll(notificationsList);
        return NotificationResponse.fromNotifications(notificationsList);
    }

    public List<NotificationResponse> getUnreadNotifications() {
        Long userId = AuthUtils.getAuthenticatedUser().getId();

        List<Notification> notificationsList = notificationRepository.findAllByUserIdAndSeenByUser(userId, false);
        return NotificationResponse.fromNotifications(notificationsList);
    }

    public void markNotificationsAsRead(List<Long> notificationIds) {
        List<Notification> notifications = notificationRepository.findAllByIdIn(notificationIds);
        notifications.forEach(notification -> notification.setSeenByUser(true));
        notificationRepository.saveAll(notifications);
    }
}
