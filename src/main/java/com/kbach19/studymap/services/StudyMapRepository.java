package com.kbach19.studymap.services;

import com.kbach19.studymap.model.StudyMap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyMapRepository extends JpaRepository<StudyMap, Long> {

}
