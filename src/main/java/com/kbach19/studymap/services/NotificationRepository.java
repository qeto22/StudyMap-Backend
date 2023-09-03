package com.kbach19.studymap.services;


import com.kbach19.studymap.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
