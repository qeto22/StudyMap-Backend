package com.kbach19.studymap.services;

import com.kbach19.studymap.api.dto.BuyRequest;
import com.kbach19.studymap.model.Course;
import com.kbach19.studymap.model.SystemUser;
import com.kbach19.studymap.utils.AuthUtils;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PaymentService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SystemUserRepository systemUserRepository;

    @Autowired
    private NotificationService notificationSerivce;

    public void buy(BuyRequest buyRequest) throws Throwable {
        validateCard(buyRequest);

        SystemUser currentSystemUser = AuthUtils.getAuthenticatedUser();
        for (Long courseId : buyRequest.getCourseIds()) {
            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new RuntimeException("Course with id " + courseId + " not found"));
            currentSystemUser.getBoughtCourses().add(course);
        }

        if (buyRequest.getMentorshipNotificationId() != null) {
            notificationSerivce.mentorshipDealFinalized(buyRequest.getMentorshipNotificationId());
        }

        systemUserRepository.save(currentSystemUser);
    }

    private void validateCard(BuyRequest buyRequest) {
        String cardHolder = buyRequest.getCardHolder();
        String cardNumber = StringUtils.deleteWhitespace(buyRequest.getCardNumber());
        String expirationDate = StringUtils.deleteWhitespace(buyRequest.getExpirationDate());
        String cvv = buyRequest.getCvv();

//        if (cardHolder == null || cardNumber == null || expirationDate == null || cvv == null) {
//            throw new RuntimeException("Invalid card credentials");
//        }
//
//        // check if expiration date is in the past
//        String[] expirationDateParts = expirationDate.split("/");
//        if (expirationDateParts.length != 2) {
//            throw new RuntimeException("Invalid expiration date");
//        }
//        int month = Integer.parseInt(expirationDateParts[0]);
//        int year = Integer.parseInt(expirationDateParts[1]);
//        if (month < 1 || month > 12 || year < 2021) {
//            throw new IllegalStateException("Invalid expiration date");
//        }
//
//        // check if cvv is 3 digits
//        if (cvv.length() != 3) {
//            throw new IllegalStateException("Invalid cvv");
//        }
    }
}
