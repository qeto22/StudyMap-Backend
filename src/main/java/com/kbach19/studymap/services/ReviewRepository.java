package com.kbach19.studymap.services;

import com.kbach19.studymap.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
