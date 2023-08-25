package com.kbach19.studymap.services;

import com.kbach19.studymap.api.dto.CreateCourseRequest;
import com.kbach19.studymap.api.dto.CreateSectionRequest;
import com.kbach19.studymap.model.Course;
import com.kbach19.studymap.model.CourseSection;
import com.kbach19.studymap.model.CourseVideo;
import com.kbach19.studymap.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private MediaServices mediaServices;

    public Long create(CreateCourseRequest request, MultipartFile image) {
        String imageUrl = mediaServices.saveImage(image);

        Course course = Course.builder()
                .title(request.getCourseTitle())
                .description(request.getCourseDescription())
                .objectives(request.getCourseLearningObjectives())
                .tags(request.getCourseTags())
                .category(request.getCourseCategory())
                .price(request.getCoursePrice())
                .imageUrl(imageUrl)
                .author(AuthUtils.getAuthenticatedUser())
                .build();
        courseRepository.save(course);

        return course.getId();
    }

    public void createSection(MultipartFile[] videos, CreateSectionRequest request) {
        List<CourseVideo> courseVideos = new ArrayList<>();

        List<String> videoTitles = request.getVideoTitles();
        for (int i = 0; i < videos.length; i++) {
            if (i >= videoTitles.size()) {
                break;
            }

            CourseVideo video = CourseVideo.builder()
                    .title(videoTitles.get(i))
                    .videoUrl(mediaServices.saveVideo(videos[i]))
                    .build();
            courseVideos.add(video);
        }

        CourseSection section = CourseSection.builder()
                .title(request.getSectionTitle())
                .videos(courseVideos)
                .build();

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("Unknown Course Id"));
        course.getCourseSectionList().add(section);

        courseRepository.save(course);
    }
}
