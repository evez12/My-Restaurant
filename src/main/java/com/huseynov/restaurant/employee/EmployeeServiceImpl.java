package com.huseynov.restaurant.employee;

import com.huseynov.restaurant.security.AuthService;
import com.huseynov.restaurant.shared.dto.request.LoginRequest;
import com.huseynov.restaurant.shared.dto.response.LoginResponse;
import com.huseynov.restaurant.shared.dto.response.RegisterResponse;
import com.huseynov.restaurant.shared.exception.CustomAuthException;
import com.huseynov.restaurant.shared.exception.CustomNotFoundException;
import com.huseynov.restaurant.shared.exception.ExistsEmailException;
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

@Service()
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
            throw new CustomAuthException("Invalid email or password");
        }

        return authenticationProcess(authentication);
    }

    @Override
    public RegisterResponse createEmployee(CreateEmployeeRequest request) {
        try {
            log.info("EmployeeServiceImpl:createEmployee execution started, request: {}", request);
            if (employeeRepository.existsEmployeeByEmail(request.getEmail())) {
                log.warn("Email already exists, email: {}", request.getEmail());
                throw new ExistsEmailException("Email already exists, email: " + request.getEmail());
            }

            Employee employee = EmployeeMapper.convertCreateEmployeeRequestToEmployee(request);
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
            return generateRegisterResponse(employee, request.getEmail(), request.getPassword());
        } catch (ExistsEmailException e) {
            throw e;
        }
        catch (RuntimeException e) {
            log.error("Error in createEmployee {}", e.getMessage());
            throw new EmployeeServiceException("Error occurred while trying to create employee with email: " + request.getEmail());
        }

    }

    public RegisterResponse createManager(Employee employee, Role role1) {
        log.info("EmployeeServiceImpl:createManager execution started");
        Role role2 = roleRepo.findByName("MANAGER");
        log.info("Role found: {}", role2);
        employee.setRoles(Set.of(role1, role2));
        log.info("Employee roles after add: {}", employee.getRoles());

        return generateRegisterResponse(employee, employee.getEmail(), employee.getPassword());

    }

    @Override
    public LoginResponse authenticationProcess(Authentication authentication) {
        log.info("EmployeeServiceImpl:authenticationProcess execution started");
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
        List<String> roles = savedEmployee.getRoles()
                .stream()
                .map(Role::getName)
                .toList();

        return new RegisterResponse(
                email,
                roles
        );
    }


    @Override
    public List<EmployeeResponse> getAllEmployees() {
        try {
            List<Employee> employees = employeeRepository.findAll();
            if (employees.isEmpty()) {
                log.warn("Employees not found in Database");
                throw new CustomNotFoundException("Employees not found in Database");
            }

            return employees
                    .stream()
                    .map(employeeMapper::convertEntityToResponse)
                    .toList();

        } catch (CustomNotFoundException e) {
            throw e;
        }
        catch (RuntimeException e) {
            log.error("Error in getAllEmployees {}", e.getMessage());
            throw new EmployeeServiceException("Error occurred while trying to get all employee with id: " );
        }
    }

    @Override
    public EmployeeResponse getEmployeeById(Long employeeId) throws EmployeeServiceException {

        try {
            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new CustomNotFoundException("Employee not found with id: " + employeeId));
            return employeeMapper.convertEntityToResponse(employee);
        } catch (CustomNotFoundException e) {
            throw e;
        } catch (RuntimeException e) {
            log.error("Error in getEmployeeById {}", e.getMessage());
            throw new EmployeeServiceException("Error occurred while trying to get employee with id: " + employeeId);
        }
    }
}

