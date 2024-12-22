package com.huseynov.restaurant.employee;

import com.huseynov.restaurant.shared.dto.request.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapperConfig employeeMapper;

    @Override
    public EmployeeResponse createEmployee(CreateUserRequest request) {
        EmployeeResponse response;
        try {
            log.info("EmployeeServiceImpl:createEmployee execution started {}", request);
            if (employeeRepository.existsEmployeeByEmail(request.getEmail())) {
                log.warn("Email already exists, email: {}", request.getEmail());
                throw new ExistsEmailException("Email already exists, email: " + request.getEmail());
            }

            Employee employee = employeeMapper.convertDtoToEntity(request);
            EmployeeDetail employeeDetail = employee.getEmployeeDetail();

            if (employeeDetail != null) {
                employeeDetail.setEmployee(employee);
            }

            Employee savedEmployee = employeeRepository.save(employee);
            response = employeeMapper.convertEntityToResponse(savedEmployee);
            return response;
        } catch (ExistsEmailException e) {
            throw e;
        } catch (Exception e) {
            log.error("Exception occurred while persisting employee to database, Exception message {}", e.getMessage());
            throw new EmployeeServiceException("Exception occurred while persisting employee to database");
        }
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

