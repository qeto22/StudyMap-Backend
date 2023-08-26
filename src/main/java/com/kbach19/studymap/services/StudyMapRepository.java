package com.kbach19.studymap.services;

import com.kbach19.studymap.model.StudyMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface StudyMapRepository extends JpaRepository<StudyMap, Long> {

    List<StudyMap> findByAuthorId(Long authorId);

    List<StudyMap> findTop4ByOrderByIdDesc();
}
