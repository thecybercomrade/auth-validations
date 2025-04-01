package com.authentication.project1.config;
import com.authentication.project1.component.JWTValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public JWTValidator jwtValidator() {
        return new JWTValidator();
    }
}
