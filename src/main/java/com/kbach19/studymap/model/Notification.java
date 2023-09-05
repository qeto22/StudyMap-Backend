package com.kbach19.studymap.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Notification {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private SystemUser user;

    @ManyToOne
    private SystemUser sender;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private String message;

    private String additionalData;

    private boolean seenByUser;

}
