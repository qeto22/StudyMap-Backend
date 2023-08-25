package com.kbach19.studymap.utils;

import com.kbach19.studymap.api.dto.Author;
import com.kbach19.studymap.model.SystemUser;

public class DtoUtils {

    public static Author toDTO(SystemUser user) {
        return Author.builder()
                .name(user.getFirstName() + " " + user.getLastName())
                .username(user.getUsername())
                .build();
    }

}
