package com.kbach19.studymap.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StudyMap extends Reviewable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private SystemUser author;

    @Column(length = Integer.MAX_VALUE)
    private String imagePath;

    private String title;

    @Column(length = Integer.MAX_VALUE)
    private String description;

    @Column(length = Integer.MAX_VALUE)
    private String mapData;

}
