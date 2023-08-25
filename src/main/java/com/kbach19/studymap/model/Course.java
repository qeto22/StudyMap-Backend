package com.kbach19.studymap.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @Column(length = Integer.MAX_VALUE)
    private String description;

    @Column(length = Integer.MAX_VALUE)
    private String tags;

    private String category;

    private BigDecimal price;

    @Column(length = Integer.MAX_VALUE)
    private String imageUrl;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CourseSection> courseSectionList = new ArrayList<>();

}
