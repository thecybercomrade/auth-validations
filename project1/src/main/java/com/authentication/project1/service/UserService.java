package com.authentication.project1.service;
import com.authentication.project1.clients.UserClient;
import com.authentication.project1.models.UserResponse;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserClient userClient;

    public UserService(UserClient userClient) {
        this.userClient = userClient;
    }

    public UserResponse getUserDetails(String token) {
        return userClient.getUserDetails(token);
    }
}

