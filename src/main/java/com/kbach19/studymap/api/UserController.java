package com.kbach19.studymap.api;

import com.kbach19.studymap.api.dto.UpdateUserRequest;
import com.kbach19.studymap.api.dto.UserDetailsResponse;
import com.kbach19.studymap.model.SystemUser;
import com.kbach19.studymap.services.MediaServices;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/user")
@Transactional
public class UserController {

    @Autowired
    private MediaServices mediaServices;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager em;

    @GetMapping
    public UserDetailsResponse getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SystemUser systemUser = (SystemUser) authentication.getPrincipal();

        return UserDetailsResponse.builder()
                .username(systemUser.getUsername())
                .email(systemUser.getEmail())
                .firstName(systemUser.getFirstName())
                .lastName(systemUser.getLastName())
                .imageUrl(systemUser.getImageUrl())
                .description(systemUser.getDescription())
                .type(systemUser.getType().name())
                .build();
    }

    @PostMapping("/update")
    public void updateUser(@RequestPart(value = "image", required = false) MultipartFile profileImage,
                           @RequestPart("request") UpdateUserRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SystemUser systemUser = (SystemUser) authentication.getPrincipal();
        systemUser.setDescription(request.getDescription());
        if (profileImage != null && !profileImage.isEmpty()) {
            systemUser.setImageUrl(mediaServices.saveImage(profileImage));
        }

        em.merge(systemUser);
    }

    @PostMapping("/update-password")
    public void updatePassword(@RequestPart(value = "newPassword") String newPassword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SystemUser systemUser = (SystemUser) authentication.getPrincipal();
        if (newPassword != null && !newPassword.isEmpty()) {
            systemUser.setPassword(passwordEncoder.encode(newPassword));
        }
        em.merge(systemUser);
    }

}
