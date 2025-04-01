package com.authentication.project1.controllers;

import com.authentication.project1.models.UserResponse;
import com.authentication.project1.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aggregator")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/userdetails")
    public UserResponse getUserDetails(@RequestHeader("Authorization") String token) {
        return userService.getUserDetails(token);
    }
}

