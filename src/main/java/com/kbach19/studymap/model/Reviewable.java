package com.kbach19.studymap.model;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class Reviewable {

    @OneToMany
    private List<Review> reviews = new ArrayList<>();

}
