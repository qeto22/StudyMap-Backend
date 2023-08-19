package com.kbach19.studymap.auth;

import com.kbach19.studymap.model.SystemUser;
import com.kbach19.studymap.services.SystemUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SystemUserAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private SystemUserRepository systemUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String usernameOrEmail = authentication.getName();
        String password = authentication.getCredentials().toString();

        SystemUser systemUser = systemUserRepository.findSystemUserByEmailOrUsername(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new BadCredentialsException("Invalid username, email or password!"));

        if (!passwordEncoder.matches(password, systemUser.getPassword())) {
            throw new BadCredentialsException("Invalid username, email or password!");
        }

        return new UsernamePasswordAuthenticationToken(systemUser, null, systemUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
