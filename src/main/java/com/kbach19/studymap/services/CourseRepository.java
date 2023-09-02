package com.kbach19.studymap.services;

import com.kbach19.studymap.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findCourseByAuthorId(Long id);

    List<Course> findCourseByAuthorUsername(String username);

    List<Course> findTop4ByOrderByIdDesc();

}
