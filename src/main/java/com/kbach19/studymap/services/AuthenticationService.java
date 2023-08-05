package com.kbach19.studymap.services;

import com.kbach19.studymap.auth.JwtService;
import com.kbach19.studymap.auth.SystemUserAuthenticationProvider;
import com.kbach19.studymap.api.dto.SignInRequest;
import com.kbach19.studymap.api.dto.SignInResponse;
import com.kbach19.studymap.api.dto.SignUpRequest;
import com.kbach19.studymap.api.dto.SignUpResponse;
import com.kbach19.studymap.auth.model.SystemUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private SystemUserRepository systemUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SystemUserAuthenticationProvider authenticationProvider;

    public SignUpResponse register(SignUpRequest request) {
        SystemUser newUser = SystemUser.builder()
                                        .firstName(request.getFirstName())
                                        .lastName(request.getLastName())
                                        .email(request.getEmail())
                                        .username(request.getUsername())
                                        .password(passwordEncoder.encode(request.getPassword()))
                                        .type(request.getSystemUserType())
                                        .build();
        systemUserRepository.save(newUser);

        String authToken = jwtService.generateToken(newUser);
        return SignUpResponse.builder()
                .token(authToken)
                .build();
    }

    public SignInResponse signIn(SignInRequest request) {
        authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsernameOrEmail(),
                request.getPassword()
        ));
        SystemUser user = systemUserRepository.findSystemUserByEmailOrUsername(request.getUsernameOrEmail(),
                                                                                request.getUsernameOrEmail())
                .orElseThrow(() -> new BadCredentialsException("Incorrect credentials were entered!"));
        String authToken = jwtService.generateToken(user);
        return SignInResponse.builder()
                .token(authToken)
                .build();
    }
}
