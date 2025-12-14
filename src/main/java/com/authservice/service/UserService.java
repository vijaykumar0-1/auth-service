package com.authservice.service;

import com.authservice.dto.UserDto;

public interface UserService {
    String registerUser(UserDto userDto);
}
