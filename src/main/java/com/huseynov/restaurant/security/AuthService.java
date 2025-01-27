package com.huseynov.restaurant.security;

import com.huseynov.restaurant.shared.exception.InvalidRequestException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    @Getter
    private final PasswordEncoder passwordEncoder;

    public Authentication authentication(String email, String password) {
        try {
            log.info("AuthService:authentication execution started");

            Authentication authentication = authManager
                    .authenticate(new UsernamePasswordAuthenticationToken(email, password));

//      Set the authentication in the security context (to be used in the future)
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("AuthService:authentication execution ended");
            return authentication;
        } catch (AuthenticationException e) {
            throw new InvalidRequestException("Email or password is invalid");
        }
    }

    public String generateJwtToken(UserDetails userDetails) {
        return jwtService.generateTokenFromUsername(userDetails);
    }


}
