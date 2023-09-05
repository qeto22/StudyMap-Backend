package com.kbach19.studymap.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kbach19.studymap.model.Notification;
import com.kbach19.studymap.model.NotificationType;
import com.kbach19.studymap.utils.DtoUtils;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class NotificationResponse {

    private long id;

    private Author sender;

    private NotificationType type;

    private String message;

    private String additionalData;

    @JsonIgnore
    public static List<NotificationResponse> fromNotifications(List<Notification> notificationsList) {
        List<NotificationResponse> responses = new ArrayList<>();
        for (Notification notification : notificationsList) {
            responses.add(fromNotification(notification));
        }
        return responses;
    }

    public static NotificationResponse fromNotification(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .sender(DtoUtils.toDTO(notification.getSender()))
                .type(notification.getType())
                .message(notification.getMessage())
                .additionalData(notification.getAdditionalData())
                .build();
    }
}
