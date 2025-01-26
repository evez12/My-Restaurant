package com.huseynov.restaurant.employee;

import com.huseynov.restaurant.employee.data.Employee;
import com.huseynov.restaurant.employee.data.EmployeeRepository;
import com.huseynov.restaurant.employee.data.MyEmployeeRepo;
import com.huseynov.restaurant.shared.UserOfSendingRequest;
import com.huseynov.restaurant.shared.exception.CustomNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
class EmployeeServiceImpl implements EmployeeService {
    private final MyEmployeeRepo myEmployeeRepo;
    private final UserOfSendingRequest userOfSendingRequest;

    @Override
    public List<Employee> getAllEmployees() {
        try {
            return myEmployeeRepo.findAllEmployees()
                    .orElseThrow(() -> new CustomNotFoundException("Employees not found in Database"));

        } catch (CustomNotFoundException e) {
            throw e;
        } catch (RuntimeException e) {
            log.error("Error in getAllEmployees {}", e.getMessage());
            throw new EmployeeServiceException("Error occurred while trying to get all employee with id: ");
        }
    }

    @Override
    public Employee getEmployeeById(Long employeeId) throws EmployeeServiceException {

        try {
            return myEmployeeRepo.findEmployeeWithRolesById(employeeId)
                    .orElseThrow(() -> new CustomNotFoundException("Employee not found with id: " + employeeId));
        } catch (CustomNotFoundException e) {
            throw e;
        } catch (RuntimeException e) {
            log.error("Error in getEmployeeById {}", e.getMessage());
            throw new EmployeeServiceException("Error occurred while trying to get employee with id: " + employeeId);
        }
    }

    @Override
    public Employee getEmployee() {
        return myEmployeeRepo.findEmployeeWithRolesByEmail(userOfSendingRequest.getEmail())
                .orElseThrow(() -> new CustomNotFoundException("Employee not found with email: " + userOfSendingRequest.getEmail()));
    }
}

