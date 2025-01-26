package com.huseynov.restaurant.shared;

import com.huseynov.restaurant.customer.AuthCustomerService;
import com.huseynov.restaurant.employee.AuthEmployeeService;
import com.huseynov.restaurant.shared.dto.request.LoginRequest;
import com.huseynov.restaurant.shared.dto.request.RegisterRequest;
import com.huseynov.restaurant.shared.dto.response.LoginResponse;
import com.huseynov.restaurant.shared.dto.response.RegisterResponse;
import com.huseynov.restaurant.shared.exception.InvalidEmailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
// This class is responsible for routing the requests to the appropriate service(EmployeeService or CustomerService)
// based on the email address of the user.
// If the email address ends with @restaurant.com,
// then the request is routed to the EmployeeService,
public class RouterService {
    private final AuthEmployeeService employeeService;
    private final AuthCustomerService authCustomerService;


    public LoginResponse login(LoginRequest loginRequest) {
        log.info("RouterService::login request ");
        // Check if the email is an employee email
        if (loginRequest.getEmail().endsWith("@restaurant.com")) {
            log.info("RouterService::login Employee login");
            return employeeService.authenticateEmployee(loginRequest);
        }
        // then it is a customer email
        else {
            log.info("RestaurantService::login Customer login");
            return authCustomerService.authenticateCustomer(loginRequest);
        }
    }

    // Register the customer
    public RegisterResponse register(RegisterRequest request) {
        log.info("RouterService::register request ");
        try {
            if (request.getEmail().endsWith("@restaurant.com")) {
                throw new InvalidEmailException("Invalid email address", request.getEmail());
            }
            return authCustomerService.register(request);
        } catch (InvalidEmailException e) {
            log.error("An error occurred while trying to register with email :{}, {}", request.getEmail(), e.getMessage());
            throw e;
        }
    }
}
