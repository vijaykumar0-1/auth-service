package com.UserManagementAPI.UserManagementAPI.controller;

import com.UserManagementAPI.UserManagementAPI.dto.UserDto;
import com.UserManagementAPI.UserManagementAPI.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;

    // Normal login controller
    @PostMapping(value = "v1/userLogin")
    public ResponseEntity<?> login( @Valid @RequestBody UserDto user)
    {
        String response = userService.userLogin(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
