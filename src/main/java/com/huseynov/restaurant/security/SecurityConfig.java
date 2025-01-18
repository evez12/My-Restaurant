package com.huseynov.restaurant.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration()
@RequiredArgsConstructor()
@EnableWebSecurity()
@EnableMethodSecurity() // For @PreAuthorize
public class SecurityConfig {
    private static final String ROLE_CUSTOMER = "CUSTOMER";
    private static final String ROLE_EMPLOYEE = "EMPLOYEE";
    private static final String ROLE_MANAGER = "MANAGER";
    private static final String ROLE_ADMIN = "ADMIN";
    private final AuthEntryPointJwt unauthorizedHandler;
    private final AuthTokenFilter authTokenFilter;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(request ->
                request
                        .requestMatchers("/api/v1/hello").permitAll()
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()

                        .requestMatchers("/api/v1/carts/**").hasAuthority(ROLE_CUSTOMER)
                        .requestMatchers("/api/v1/customer/**").hasAuthority(ROLE_CUSTOMER)
                        .requestMatchers("/api/v1/employee/**").hasAuthority(ROLE_EMPLOYEE)

                        .requestMatchers("/api/v1/manager/**").hasAuthority(ROLE_MANAGER)
                        .requestMatchers("/api/v1/categories/**").hasAuthority(ROLE_MANAGER)
                        .requestMatchers("/api/v1/products/**").hasAuthority(ROLE_MANAGER)

                        .requestMatchers("/api/v1/employees/**").hasAuthority(ROLE_MANAGER)
//                        .requestMatchers("/api-docs/**").hasAuthority(ROLE_ADMIN)
                        .requestMatchers("/**").hasAuthority(ROLE_ADMIN)   // this should be the last line
                        .anyRequest().authenticated() // any other request should be authenticated
        );

        httpSecurity.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity.exceptionHandling(exception ->
                exception.authenticationEntryPoint(unauthorizedHandler));

        httpSecurity
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // we don't need sessions

        httpSecurity
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }


    @Bean
    DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // default strength  is 10
    }

}
