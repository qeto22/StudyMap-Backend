package com.kbach19.studymap.api.dto;

import com.kbach19.studymap.auth.model.SystemUserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    private SystemUserType userType;

    private String firstName;

    private String lastName;

    private String email;

    private String username;

    private String password;

}
