package com.huseynov.restaurant.customer;

import com.huseynov.restaurant.security.AuthService;
import com.huseynov.restaurant.shared.dto.request.LoginRequest;
import com.huseynov.restaurant.shared.dto.request.RegisterRequest;
import com.huseynov.restaurant.shared.dto.response.LoginResponse;
import com.huseynov.restaurant.shared.dto.response.RegisterResponse;
import com.huseynov.restaurant.shared.exception.CustomAuthException;
import com.huseynov.restaurant.shared.exception.ExistsEmailException;
import com.huseynov.restaurant.shared.model.Role;
import com.huseynov.restaurant.shared.model.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final RoleRepository roleRepo;
    private final CustomerRepository customerRepo;
    private final AuthService authService;

    @Override
    public LoginResponse authenticateCustomer(LoginRequest request) {
        log.info("CustomerServiceImpl:authenticateCustomer execution started");
        try {
            Authentication authentication = authService.authentication(request.getEmail(), request.getPassword());
            return authenticationProcess(authentication);
        } catch (RuntimeException e) {
            log.error("Error in authentication {}", e.getMessage());
            throw new CustomAuthException("Invalid email or password");
        }
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {
        log.info("CustomerServiceImpl:register execution started");
        try {
            if (customerRepo.existsCustomerByEmail(request.getEmail())) {
                log.warn("Email already exists, email: {}", request.getEmail());
                throw new ExistsEmailException();
            }
            Customer customer = CustomerMapper.convertRegisterrequestToCustomer(request);
            customer.setPassword(authService
                    .getPasswordEncoder()
                    .encode(request.getPassword())
            );
            Role role1 = roleRepo.findByName("CUSTOMER"); // default role is CUSTOMER
            customer.setRoles(Set.of(role1));
            customerRepo.save(customer);

            Authentication authentication = authService.authentication(request.getEmail(), request.getPassword());
            LoginResponse loginResponse = authenticationProcess(authentication);
            return new RegisterResponse(loginResponse.getEmail(), loginResponse.getToken(), loginResponse.getRoles());

        } catch (ExistsEmailException e) {
            log.error("Error in registration {}", e.getMessage());
            throw new ExistsEmailException("Email already exists, email: " + request.getEmail());
        }
    }

    @Override
    public LoginResponse authenticationProcess(Authentication authentication) {
        log.info("CustomerServiceImpl:authenticationProcess execution started");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwtToken = authService.generateJwtToken(userDetails);
        List<String> roles = userDetails
                .getAuthorities()
                .stream()
                .map(Objects::toString)
                .toList();

        return new LoginResponse(
                userDetails.getUsername(),
                jwtToken,
                roles
        );
    }


}
