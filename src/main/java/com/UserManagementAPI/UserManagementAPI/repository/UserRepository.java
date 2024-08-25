package com.UserManagementAPI.UserManagementAPI.repository;

import com.UserManagementAPI.UserManagementAPI.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
@RequiredArgsConstructor
public class UserRepository {
    public final MongoTemplate mongoTemplate;

    public void createUser(User user) {
        mongoTemplate.save(user,"User");
        mongoTemplate.save(user);
    }
}
