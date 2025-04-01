package com.authentication.project1.interceptor;

import com.authentication.project1.component.JWTValidator;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Arrays;

@Component
@Slf4j
public class JwtAuthenticationFilter implements Filter {

    private final JWTValidator jwtValidator;

    public JwtAuthenticationFilter(JWTValidator jwtValidator) {
        this.jwtValidator = jwtValidator;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String token = extractToken(httpRequest);

        try {
            boolean tokenValidRes = jwtValidator.validateToken(token);
            if (!tokenValidRes) {
                log.warn("Token is not valid");
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
                return;
            }
        } catch (Exception e) {
            log.error("Error in Token validation {}", e.getMessage());
            httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Token validation error");
            return;
        }

        chain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        Cookie[] cookie = request.getCookies();
        String JWTToken = Arrays.stream(cookie).toList()
                .stream()
                .filter(rec -> rec.getName().equals("jwt"))
                .map(Cookie::getValue)
                .findFirst()
                .get();
//        String header = request.getHeader("Authorization");
//        return (header != null && header.startsWith("Bearer ")) ? header.substring(7) : null;
        return JWTToken;
    }

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}
}
