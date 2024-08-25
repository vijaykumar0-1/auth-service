package com.UserManagementAPI.UserManagementAPI.service.Impl;

import com.UserManagementAPI.UserManagementAPI.dto.UserDto;
import com.UserManagementAPI.UserManagementAPI.model.User;
import com.UserManagementAPI.UserManagementAPI.repository.UserRepository;
import com.UserManagementAPI.UserManagementAPI.repository.UserRepositoryInterface;
import com.UserManagementAPI.UserManagementAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRepositoryInterface userRepositoryInterface;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String createUser(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        if (userRepositoryInterface.findByEmail(user.getEmail()) == null &&
                userRepositoryInterface.findByUsername(user.getUsername()) == null) {
            user.setPassword(hashedPassword);
            userRepository.createUser(user);
            return "user Created";
        }
        return "user already exists !";
    }

    @Override
    public String userLogin(UserDto user) {
        User returnedUser = userRepositoryInterface.findByEmail(user.getEmail());
        if(returnedUser != null)
        {
            if (passwordEncoder.matches(user.getPassword(), returnedUser.getPassword()))
                return "Login successful !";
        }
        return "Invalid email or password";
    }
}
