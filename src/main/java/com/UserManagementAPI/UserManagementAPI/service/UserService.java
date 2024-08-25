package com.UserManagementAPI.UserManagementAPI.service;

import com.UserManagementAPI.UserManagementAPI.dto.UserDto;
import com.UserManagementAPI.UserManagementAPI.model.User;

public interface UserService {
    String createUser(User user);
    String userLogin(UserDto user);
}
