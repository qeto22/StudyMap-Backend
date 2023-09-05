package com.kbach19.studymap.services;

import com.kbach19.studymap.model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    List<Meeting> findByMentorIdOrMenteeId(Long mentorId, Long menteeId);

}
