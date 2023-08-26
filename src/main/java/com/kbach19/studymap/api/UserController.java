package com.kbach19.studymap.api;

import com.kbach19.studymap.api.dto.UserDetailsResponse;
import com.kbach19.studymap.model.SystemUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

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
                .type(systemUser.getType().name())
                .build();
    }

}
