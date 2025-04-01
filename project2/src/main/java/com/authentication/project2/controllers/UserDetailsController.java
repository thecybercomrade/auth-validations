package com.authentication.project2.controllers;

import com.nimbusds.jwt.SignedJWT;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/data")
public class UserDetailsController {

    @GetMapping("/userdetails")
    public String userDetails(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        try {
            Optional<String> tokenOpt = extractToken(authHeader);
            if (tokenOpt.isEmpty()) {
                return "Token Missing";
            }

            SignedJWT jwt = SignedJWT.parse(tokenOpt.get());
            String userId = jwt.getJWTClaimsSet().getStringClaim("sub");

            return "Data for User: " + userId;
        } catch (Exception e) {
            return "Error Processing Token";
        }
    }

    private Optional<String> extractToken(String header) {
        if (header != null && header.startsWith("Bearer ")) {
            return Optional.of(header.substring(7));
        }
        return Optional.empty();
    }
}
