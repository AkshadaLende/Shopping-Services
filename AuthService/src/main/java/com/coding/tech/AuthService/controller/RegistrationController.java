package com.coding.tech.AuthService.controller;

import com.coding.tech.AuthService.dto.RegistrationRequest;
import com.coding.tech.AuthService.entity.User;
import com.coding.tech.AuthService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class RegistrationController {
    public static final Logger log  = LoggerFactory.getLogger(LoggerFactory.class);

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody RegistrationRequest registrationRequest) {
        log.info("Inside registerUser method");

        return ResponseEntity.ok(userService.register(registrationRequest));
    }
}
