package com.kbach19.studymap.services;

import com.kbach19.studymap.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
