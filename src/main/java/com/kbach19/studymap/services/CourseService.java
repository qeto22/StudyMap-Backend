package com.kbach19.studymap.services;

import com.kbach19.studymap.api.dto.*;
import com.kbach19.studymap.model.*;
import com.kbach19.studymap.utils.AuthUtils;
import com.kbach19.studymap.utils.DtoUtils;
import com.kbach19.studymap.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private MediaServices mediaServices;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private SystemUserRepository systemUserRepository;

    public Long create(CreateCourseRequest request, MultipartFile image) {
        String imageUrl = "/image/" +  mediaServices.saveImage(image);

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
                    .videoUrl("/video/" + mediaServices.saveVideo(videos[i]))
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

    public CourseResponse getCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Unknown Course Id"));
        return toDTO(course);
    }

    public List<CourseResponse> getAuthoredCourses() {
        SystemUser user = AuthUtils.getAuthenticatedUser();
        return toDTO(courseRepository.findCourseByAuthorId(user.getId()));
    }

    public List<CourseResponse> getBoughtCourses() {
        Long currentUserId = AuthUtils.getAuthenticatedUser().getId();
        SystemUser user = systemUserRepository.findById(currentUserId)
                .orElseThrow(() -> new IllegalArgumentException("Unknown User Id"));
        return toDTO(user.getBoughtCourses());
    }

    public List<CourseResponse> getPopularCourses() {
        return toDTO(courseRepository.findTop4ByOrderByIdDesc());
    }

    private List<CourseResponse> toDTO(List<Course> courses) {
        List<CourseResponse> courseResponseList = new ArrayList<>();
        for (Course course : courses) {
            courseResponseList.add(toDTO(course));
        }
        return courseResponseList;
    }

    private CourseResponse toDTO(Course course) {
        return CourseResponse.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .objectives(course.getObjectives())
                .tags(course.getTags())
                .category(course.getCategory())
                .price(course.getPrice())
                .imageUrl(course.getImageUrl())
                .author(DtoUtils.toDTO(course.getAuthor()))
                .sections(course.getCourseSectionList()
                        .stream()
                        .map(courseSection -> SectionResponse.builder()
                                .title(courseSection.getTitle())
                                .videos(courseSection.getVideos()
                                        .stream()
                                        .map(courseVideo -> VideoResponse.builder()
                                                .title(courseVideo.getTitle())
                                                .videoUrl(courseVideo.getVideoUrl())
                                                .build())
                                        .collect(Collectors.toList()))
                                .build())
                        .collect(Collectors.toList()))
                .reviews(course.getReviews()
                        .stream()
                        .map(review -> ReviewResponse.builder()
                                .rating(review.getRating())
                                .reviewText(review.getComment())
                                .author(DtoUtils.toDTO(review.getAuthor()))
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    public List<CourseResponse> getAuthorCourses(String username) {
        return toDTO(courseRepository.findCourseByAuthorUsername(username));
    }

    public Review postReview(PostReviewRequest request, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Unknown Course Id"));

        if (ValidationUtils.hasPostedReview(course.getReviews())) {
            throw new IllegalArgumentException("You have already posted a review for this study map");
        }

        Review review = DtoUtils.getReview(request);
        review = reviewRepository.save(review);

        course.getReviews().add(review);
        courseRepository.save(course);
        return review;
    }
}
