package com.kbach19.studymap.api;

import com.kbach19.studymap.api.dto.Course;
import com.kbach19.studymap.api.dto.CourseSearchResponse;
import com.kbach19.studymap.api.dto.TopStudyMapsResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/marketing")
public class MarketingController {

    @GetMapping("/top-study-maps")
    public ResponseEntity<TopStudyMapsResponse> getTopStudyMaps() {
        return ResponseEntity.ok(new TopStudyMapsResponse());
    }

    @GetMapping("/courses")
    public ResponseEntity<CourseSearchResponse> getCourses(@RequestParam String query) {
        Course course = Course.builder()
                .id(1L)
                .title("Journey I took to become Java developer")
                .author("Ketevan Bachalashvili")
                .courseIconImageUrl("https://media.licdn.com/dms/image/C4D03AQEV9v3FiWwyuw/profile-displayphoto-shrink_800_800/0/1635665530246?e=2147483647&v=beta&t=3H--_iRB_mZuKpjExzlFiS_PKRwBnfnUMAJhDpoMa5c")
                .build();

        Course course1 = Course.builder()
                .id(2L)
                .title("How to become a good teacher")
                .author("Nani Balakhadze")
                .courseIconImageUrl("https://media.licdn.com/dms/image/C4D03AQEV9v3FiWwyuw/profile-displayphoto-shrink_800_800/0/1635665530246?e=2147483647&v=beta&t=3H--_iRB_mZuKpjExzlFiS_PKRwBnfnUMAJhDpoMa5c")
                .build();

        List<Course> courseList = new ArrayList<>();
        courseList.add(course);
        courseList.add(course1);

        courseList = courseList.stream()
                .filter(courseToFilter -> StringUtils.containsIgnoreCase(courseToFilter.getTitle(), query))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CourseSearchResponse.builder()
                .courses(courseList)
                .build());
    }

}
