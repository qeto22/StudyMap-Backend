package com.kbach19.studymap.services;

import com.kbach19.studymap.auth.model.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SystemUserRepository extends JpaRepository<SystemUser, Long> {

    Optional<SystemUser> findSystemUserByUsername(String username);

    Optional<SystemUser> findSystemUserByEmailOrUsername(String email, String username);

}
