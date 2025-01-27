package com.huseynov.restaurant.customer;

import com.huseynov.restaurant.customer.data.Customer;
import com.huseynov.restaurant.customer.data.CustomerRepository;
import com.huseynov.restaurant.security.AuthService;
import com.huseynov.restaurant.shared.dto.request.LoginRequest;
import com.huseynov.restaurant.shared.dto.request.RegisterRequest;
import com.huseynov.restaurant.shared.dto.response.LoginResponse;
import com.huseynov.restaurant.shared.dto.response.RegisterResponse;
import com.huseynov.restaurant.shared.exception.ExistsItemException;
import com.huseynov.restaurant.shared.exception.InvalidRequestException;
import com.huseynov.restaurant.shared.model.Role;
import com.huseynov.restaurant.shared.model.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthCustomerServiceImpl implements AuthCustomerService {
    private final RoleRepository roleRepo;
    private final CustomerRepository customerRepo;
    private final AuthService authService;

    @Override
    public LoginResponse authenticateCustomer(LoginRequest request) {
        Authentication authentication;
        try {
            log.info("CustomerServiceImpl:authenticateCustomer execution started");
            authentication = authService.authentication(request.getEmail(), request.getPassword());
        } catch (BadCredentialsException | InvalidRequestException e) {
            log.error("Exception occurred while during customer authentication; REASON Bad Credential, email: {}, Exception message: {}, Exception name: {}"
                    , request.getEmail(), e.getMessage(), e.getClass().getName());
            throw new InvalidRequestException("Invalid email or password");
        } catch (RuntimeException e) {
            log.error("Exception occurred while during customer authentication; email: {}, Exception message: {}, Exception name; {}",
                    request.getEmail(), e.getMessage(), e.getClass().getName());
            throw new CustomerServiceException("Exception occurred while during customer authentication; email: " + request.getEmail());
        }
        log.info("AuthCustomerServiceImpl:authenticateCustomer execution ended");
        return authenticationProcess(authentication);
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {
        LoginResponse loginResponse;
        try {
            log.info("CustomerServiceImpl:register execution started");
            if (customerRepo.existsCustomerByEmail(request.getEmail())) {
                log.warn("Email already exists, email: {}", request.getEmail());
                throw new ExistsItemException();
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
            loginResponse = authenticationProcess(authentication);

        } catch (ExistsItemException e) {
            log.error("Error in registration {}", e.getMessage());
            throw new ExistsItemException("Email already exists, email: " + request.getEmail());
        } catch (Exception e) {
            log.error("Exception occurred while employee register email: {}. Exception message: {}", request.getEmail(), e.getMessage());
            throw new CustomerServiceException("Exception occurred while register processing, email: " + request.getEmail());
        }
        log.info("AuthCustomerServiceImpl:register execution ended");
        return new RegisterResponse(loginResponse.getEmail(), loginResponse.getToken(), loginResponse.getRoles());
    }

    @Override
    public LoginResponse authenticationProcess(Authentication authentication) {
        try {
            log.info("AuthCustomerServiceImpl:authenticationProcess execution started");

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwtToken = authService.generateJwtToken(userDetails);
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(Objects::toString)
                    .toList();

            log.info("AuthCustomerServiceImpl:authenticationProcess execution ended");
            return new LoginResponse(userDetails.getUsername(), jwtToken, roles);
        } catch (Exception e) {
            log.error("Exception occurred during customer authentication processing; email: {}, Exception message: {}",
                    ((UserDetails) authentication.getPrincipal()).getUsername(), e.getMessage());
            throw new CustomerServiceException("Exception occurred during customer authentication processing; email: " +
                    ((UserDetails) authentication.getPrincipal()).getUsername());
        }
    }
}
