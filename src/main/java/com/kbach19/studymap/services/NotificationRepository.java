package com.kbach19.studymap.services;


import com.kbach19.studymap.model.Notification;
import com.kbach19.studymap.model.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByUserIdAndSeenByUser(Long userId, boolean seenByUser);

    List<Notification> findAllByIdIn(List<Long> notificationIds);

    List<Notification> findAllByUserId(Long userId);
}
