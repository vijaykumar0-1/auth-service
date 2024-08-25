package com.UserManagementAPI.UserManagementAPI.controller;

import com.UserManagementAPI.UserManagementAPI.model.User;
import com.UserManagementAPI.UserManagementAPI.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping(path = "user/register")
    public ResponseEntity<?> userRegistration(@Valid @RequestBody User user)
    {
        String response = userService.createUser(user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
