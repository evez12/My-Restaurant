package com.huseynov.restaurant.employee;

import com.huseynov.restaurant.security.AuthService;
import com.huseynov.restaurant.security.CustomAuthException;
import com.huseynov.restaurant.shared.dto.request.LoginRequest;
import com.huseynov.restaurant.shared.dto.response.LoginResponse;
import com.huseynov.restaurant.shared.dto.response.RegisterResponse;
import com.huseynov.restaurant.shared.model.Role;
import com.huseynov.restaurant.shared.model.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepo;
    private final EmployeeMapper employeeMapper;
    private final AuthService authService;

    @Override
    public LoginResponse authenticateEmployee(LoginRequest request) {
        log.info("EmployeeServiceImpl:authenticateEmployee execution started, request: {}", request);
        Authentication authentication;
        try {
            authentication = authService.authentication(request.getEmail(), request.getPassword());
        } catch (AuthenticationException e) {
            log.error("Error in authentication {}", e.getMessage());
            throw new CustomAuthException("Error in authentication");
        }

        return generateLoginResponse(authentication);
    }

    @Override
    public RegisterResponse createEmployee(CreateEmployeeRequest request) {
        try {
            log.info("EmployeeServiceImpl:createEmployee execution started, request: {}", request);
            if (employeeRepository.existsEmployeeByEmail(request.getEmail())) {
                log.warn("Email already exists, email: {}", request.getEmail());
                throw new ExistsEmailException("Email already exists, email: " + request.getEmail());
            }

            Employee employee = employeeMapper.convertDtoToEntity(request);
            employee.setPassword(authService
                    .getPasswordEncoder()
                    .encode(request.getPassword()));

            Role role1 = roleRepo.findByName("EMPLOYEE");
            employee.setRoles(Set.of(role1));
            EmployeeDetail employeeDetail = employee.getEmployeeDetail();
            employeeDetail.setEmployee(employee);
            employee.setEmployeeDetail(employeeDetail);

            if ("MANAGER".equals(request.getRole())) {
                return createManager(employee);
            }

            return generateRegisterResponse(employee, request.getEmail(), request.getPassword());
        } catch (ExistsEmailException e) {
            throw e;
        } catch (Exception e) {
            log.error("Exception occurred while persisting employee to database, Exception message {}", e.getMessage());
            throw new EmployeeServiceException("Exception occurred while persisting employee to database");
        }

    }

    public RegisterResponse createManager(Employee employee) {
        try {
            log.info("EmployeeServiceImpl:createManager execution started");

            Role role1 = roleRepo.findByName("MANAGER");
            employee.getRoles().add(role1);

            return generateRegisterResponse(employee, employee.getEmail(), employee.getPassword());
        } catch (ExistsEmailException e) {
            throw e;
        } catch (Exception e) {
            log.error("Exception occurred while persisting employee to database, Exception message {}", e.getMessage());
            throw new EmployeeServiceException("Exception occurred while persisting employee to database");
        }

    }

    @Override
    public LoginResponse generateLoginResponse(Authentication authentication) {
        log.info("EmployeeServiceImpl:generateLoginResponse execution started");
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

    @Override
    public RegisterResponse generateRegisterResponse(Employee employee, String email, String password) {
        log.info("EmployeeServiceImpl:generateRegisterResponse execution started");
        Employee savedEmployee = employeeRepository.save(employee);
        log.debug("Employee saved: {}", savedEmployee);

        Authentication authentication = authService.authentication(email, password);
        LoginResponse loginResponse = generateLoginResponse(authentication);
        return new RegisterResponse(
                loginResponse.getEmail(),
                loginResponse.getToken(),
                loginResponse.getRoles()
        );
    }


    @Override
    public List<EmployeeResponse> getAllEmployees() {
        try {
            List<Employee> employees = employeeRepository.findAll();
            if (employees.isEmpty()) {
                log.warn("Employees not found in Database");
                throw new EmployeeNotFoundException("Employees not found in Database");
            }

            return employees
                    .stream()
                    .map(employeeMapper::convertEntityToResponse)
                    .toList();

        } catch (Exception e) {
            log.error("Exception occurred while fetch all employees from Database, Exception message: {}", e.getMessage());
            throw new EmployeeServiceException("Exception occurred while fetching all employees from Database");
        }
    }

    @Override
    public EmployeeResponse getEmployeeById(Long employeeId) throws EmployeeServiceException {

        try {
            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + employeeId));
            return employeeMapper.convertEntityToResponse(employee);
        } catch (EmployeeNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Exception occurred while fetch employee from Database with id: {}", employeeId, e);
            throw new EmployeeServiceException("Exception occurred while fetching employee from Database with id: " + employeeId);
        }
    }
}

