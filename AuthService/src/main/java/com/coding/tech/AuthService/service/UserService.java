package com.coding.tech.AuthService.service;

import com.coding.tech.AuthService.dto.RegistrationRequest;
import com.coding.tech.AuthService.entity.User;


public interface UserService {

    public User register(RegistrationRequest registrationRequest);
}
