package com.huseynov.restaurant.security;

import com.huseynov.restaurant.shared.dto.error.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

@Configuration
@Slf4j
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    // this method is used to handle unauthorized requests
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        log.error("Unauthorized :{}", authException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ApiError error = new ApiError(HttpStatus.UNAUTHORIZED,
                "path: " + request.getServletPath() + "; " + authException.getMessage(),
                authException);

        response.getWriter().write(error.toString());
    }
}
