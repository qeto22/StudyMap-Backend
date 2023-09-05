package com.kbach19.studymap.services;

import com.kbach19.studymap.api.dto.*;
import com.kbach19.studymap.model.Meeting;
import com.kbach19.studymap.model.Notification;
import com.kbach19.studymap.model.NotificationType;
import com.kbach19.studymap.model.SystemUser;
import com.kbach19.studymap.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

@Service
public class NotificationService {

    @Autowired
    private SystemUserRepository systemUserRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private MeetingRepository meetingRepository;

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

    public void acceptMentorship(AcceptMentorshipRequest request) throws Throwable {
        Notification requestNotification = notificationRepository.findById(request.getNotificationId())
                .orElseThrow((Supplier<Throwable>) () -> new IllegalStateException("Notification with id " + request.getNotificationId() + " not found"));
        requestNotification.setAdditionalData(requestNotification.getAdditionalData() + ",ACCEPTED");
        notificationRepository.save(requestNotification);

        Notification notification = Notification.builder()
                .user(requestNotification.getSender())
                .sender(AuthUtils.getAuthenticatedUser())
                .type(NotificationType.MENTORSHIP_ACCEPTED)
                .message(request.getMessage())
                .additionalData(String.join(",", request.getMeetingDates()))
                .seenByUser(false)
                .build();
        notificationRepository.save(notification);
    }

    public void rejectMentorship(RejectMentorshipRequest request) throws Throwable {
        Notification requestNotification = notificationRepository.findById(request.getNotificationId())
                .orElseThrow((Supplier<Throwable>) () -> new IllegalStateException("Notification with id " + request.getNotificationId() + " not found"));
        requestNotification.setAdditionalData(requestNotification.getAdditionalData() + ",REJECT");
        notificationRepository.save(requestNotification);

        Notification notification = Notification.builder()
                .user(requestNotification.getSender())
                .sender(AuthUtils.getAuthenticatedUser())
                .type(NotificationType.MENTORSHIP_REJECTED)
                .seenByUser(false)
                .build();
        notificationRepository.save(notification);
    }

    public void rejectAcceptedMentorship(RejectAcceptedMentorshipRequest request) throws Throwable {
        Notification requestNotification = notificationRepository.findById(request.getNotificationId())
                .orElseThrow((Supplier<Throwable>) () -> new IllegalStateException("Notification with id " + request.getNotificationId() + " not found"));
        requestNotification.setAdditionalData(requestNotification.getAdditionalData() + ",REJECT");
        notificationRepository.save(requestNotification);
    }

    public void mentorshipDealFinalized(Long mentorshipRequestId) throws Throwable {
        Notification requestNotification = notificationRepository.findById(mentorshipRequestId)
                .orElseThrow((Supplier<Throwable>) () -> new IllegalStateException("Notification with id " + mentorshipRequestId + " not found"));
        requestNotification.setAdditionalData(requestNotification.getAdditionalData() + ",ACCEPTED");
        notificationRepository.save(requestNotification);

        String[] dates = requestNotification.getAdditionalData()
                .replaceAll(",ACCEPTED", "")
                .split(",");

        for (String dateStr : dates) {
            Date meetingDate = Date.from(Instant.parse(dateStr));

            Meeting meeting = Meeting.builder()
                    .date(meetingDate)
                    .mentor(requestNotification.getSender())
                    .mentee(requestNotification.getUser())
                    .build();
            meetingRepository.save(meeting);
        }

    }
}
