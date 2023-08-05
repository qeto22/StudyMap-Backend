package com.kbach19.studymap.services;

import com.kbach19.studymap.auth.model.SystemUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SystemUserService {

    @Autowired
    private SystemUserRepository systemUserRepository;

    public SystemUser loadUserByUsername(String username) {
        return systemUserRepository.findSystemUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with given username[" + username + "] was not found!"));
    }

}
