package com.huseynov.restaurant.employee;

import com.huseynov.restaurant.customer.CustomerServiceException;
import com.huseynov.restaurant.employee.data.Employee;
import com.huseynov.restaurant.employee.data.EmployeeDetail;
import com.huseynov.restaurant.employee.data.EmployeeRepository;
import com.huseynov.restaurant.security.AuthService;
import com.huseynov.restaurant.shared.dto.request.LoginRequest;
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
@Slf4j
@RequiredArgsConstructor
public class AuthEmployeeServiceImpl implements AuthEmployeeService {
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepo;
    private final AuthService authService;
    private final EmployeeMapper employeeMapper;

    /// Order sabah hell olmali
    /// frontend dev tapilmali

    @Override
    public LoginResponse authenticateEmployee(LoginRequest request) {
        Authentication authentication;
        try {
            log.info("EmployeeServiceImpl:authenticateEmployee execution started, request: {}", request);
            authentication = authService.authentication(request.getEmail(), request.getPassword());
        } catch (BadCredentialsException | InvalidRequestException e) {
            log.error("Exception occurred while during employee authentication; REASON Bad Credential, email: {}, Exception message: {}, Exception name: {}"
                    , request.getEmail(), e.getMessage(), e.getClass().getName());
            throw new InvalidRequestException("Invalid email or password");
        } catch (Exception e) {
            log.error("Exception occurred while during employee authentication, email: {}, Exception message: {}", request.getEmail(), e.getMessage());
            throw new CustomerServiceException("Exception occurred while during employee authentication, email: " + request.getEmail());
        }
        log.info("AuthEmployeeServiceImpl:authenticateEmployee execution ended");
        return authenticationProcess(authentication);
    }

    @Override
    public RegisterResponse createEmployee(CreateEmployeeRequest request) {
        try {
            log.info("EmployeeServiceImpl:createEmployee execution started, email: {}", request.getEmail());

            if (employeeRepository.existsEmployeeByEmail(request.getEmail())) {
                log.warn("Email already exists, email: {}", request.getEmail());
                throw new ExistsItemException("Email already exists, email: " + request.getEmail());
            }

            Employee employee = employeeMapper.createEmployeeRequestToEntity(request);
            employee.setPassword(authService
                    .getPasswordEncoder()
                    .encode(request.getPassword()));

            Role role1 = roleRepo.findByName("EMPLOYEE");
            EmployeeDetail employeeDetail = employee.getEmployeeDetail();
            employeeDetail.setEmployee(employee);
            employee.setEmployeeDetail(employeeDetail);

            if ("MANAGER".equals(request.getRole())) {
                return createManager(employee, role1);
            }
            employee.setRoles(Set.of(role1));
            log.info("AuthEmployeeServiceImpl:createEmployee execution ended");
            return generateRegisterResponse(employee, request.getEmail(), request.getPassword());
        } catch (ExistsItemException e) {
            log.error("Exception occurred while during create employee; REASON already exists email: {}, Exception message: {}", request.getEmail(), e.getMessage());
            throw e;
        } catch (RuntimeException e) {
            log.error("Exception occurred while during create employee; email: {}, Exception message: {}", request.getEmail(), e.getMessage());
            throw new EmployeeServiceException("Error occurred while trying to create employee with email: " + request.getEmail());
        }
    }

    public RegisterResponse createManager(Employee employee, Role role1) {
        try {
            log.info("EmployeeServiceImpl:createManager execution started");
            Role role2 = roleRepo.findByName("MANAGER");
            log.info("Role2 found: {}", role2);
            employee.setRoles(Set.of(role1, role2));
            log.info("Employee roles after add: {}", employee.getRoles());
        } catch (RuntimeException e) {
            log.error("Exception occurred while during create manager; email: {}, Exception message: {}", employee.getEmail(), e.getMessage());
            throw new EmployeeServiceException("Exception occurred while during create manager, email: " + employee.getEmail());
        }
        log.info("AuthEmployeeServiceImpl:createManager execution ended");
        return generateRegisterResponse(employee, employee.getEmail(), employee.getPassword());

    }

    @Override
    public LoginResponse authenticationProcess(Authentication authentication) {

        try {
            log.info("AuthEmployeeServiceImpl:authenticationProcess execution started");

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwtToken = authService.generateJwtToken(userDetails);
            List<String> roles = userDetails
                    .getAuthorities()
                    .stream()
                    .map(Objects::toString)
                    .toList();

            log.info("AuthEmployeeServiceImpl:authenticationProcess execution ended");
            return new LoginResponse(userDetails.getUsername(), jwtToken, roles);
        } catch (Exception e) {
            log.error("Exception occurred during employee authentication processing; email: {}, Exception message: {}",
                    ((UserDetails) authentication.getPrincipal()).getUsername(), e.getMessage());
            throw new CustomerServiceException("Exception occurred during employee authentication processing; email: " +
                    ((UserDetails) authentication.getPrincipal()).getUsername());
        }

    }

    @Override
    public RegisterResponse generateRegisterResponse(Employee employee, String email, String password) {
        log.info("EmployeeServiceImpl:generateRegisterResponse execution started");
        Employee savedEmployee = employeeRepository.save(employee);
        log.debug("Employee saved: {}", savedEmployee);
        List<String> roles = savedEmployee.getRoles()
                .stream()
                .map(Role::getName)
                .toList();
        log.info("EmployeeServiceImpl:generateRegisterResponse execution ended");
        return new RegisterResponse(
                email,
                roles
        );
    }
}
