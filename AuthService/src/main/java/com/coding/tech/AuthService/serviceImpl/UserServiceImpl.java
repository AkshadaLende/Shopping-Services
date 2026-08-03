package com.coding.tech.AuthService.serviceImpl;

import com.coding.tech.AuthService.dto.RegistrationRequest;
import com.coding.tech.AuthService.entity.User;
import com.coding.tech.AuthService.repository.UserRepository;
import com.coding.tech.AuthService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    public static final Logger log = LoggerFactory.getLogger(LoggerFactory.class);

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;


    @Override
    public User register(RegistrationRequest registrationRequest) {
        Optional<User> user = userRepository.findByUsername(registrationRequest.getUsername());
        if (user.isPresent()) {
            log.debug("Username already exist");
            throw new RuntimeException("Username already exists");
        }

        User registerUser = User.builder().username(registrationRequest.getUsername())
                .email(registrationRequest.getEmail())
                .password(encoder.encode(registrationRequest.getPassword()))
                .roles(Set.of("ROLE_USER"))
                .createdDt(LocalDateTime.now())
                .updatedDt(LocalDateTime.now())
                .build();
        return userRepository.save(registerUser);
    }
}
