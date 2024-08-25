package com.UserManagementAPI.UserManagementAPI.service.Impl;

import com.UserManagementAPI.UserManagementAPI.model.User;
import com.UserManagementAPI.UserManagementAPI.repository.UserRepository;
import com.UserManagementAPI.UserManagementAPI.repository.UserRepositoryInterface;
import com.UserManagementAPI.UserManagementAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRepositoryInterface userRepositoryInterface;

    @Override
    public String createUser(User user) {
        if (userRepositoryInterface.findByEmail(user.getEmail()) == null &&
                userRepositoryInterface.findByUsername(user.getUsername()) == null) {
            userRepository.createUser(user);
            return "user Created";
        }
        return "user already exists !";
    }
}
