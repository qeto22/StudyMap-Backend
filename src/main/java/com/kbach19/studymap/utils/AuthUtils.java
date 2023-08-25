package com.kbach19.studymap.utils;

import com.kbach19.studymap.model.SystemUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtils {

    public static SystemUser getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (SystemUser) authentication.getPrincipal();
    }

}
