package com.authentication.project1.clients;

import com.authentication.project1.models.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "data-service", url = "http://localhost:8081")
public interface UserClient {

    @GetMapping("/data/userdetails")
    UserResponse getUserDetails(@RequestHeader("Authorization") String token);
}
