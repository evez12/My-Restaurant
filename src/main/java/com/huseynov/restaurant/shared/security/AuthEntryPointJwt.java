package com.huseynov.restaurant.shared.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huseynov.restaurant.shared.dto.ErrorDTO;
import com.huseynov.restaurant.shared.dto.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.util.Collections;


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

        ApiResponse<String> body = new ApiResponse<>();
        body.setStatus(HttpStatus.UNAUTHORIZED.toString());
        ErrorDTO error = new ErrorDTO("path: " + request.getServletPath(), authException.getMessage());

        body.setErrors(Collections.singletonList(error));


        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }
}
