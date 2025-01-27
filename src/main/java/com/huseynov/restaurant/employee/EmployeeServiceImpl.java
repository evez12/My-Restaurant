package com.huseynov.restaurant.employee;

import com.huseynov.restaurant.employee.data.Employee;
import com.huseynov.restaurant.employee.data.EmployeeRepository;
import com.huseynov.restaurant.employee.data.MyEmployeeRepo;
import com.huseynov.restaurant.employee.view.EmployeeDTO;
import com.huseynov.restaurant.employee.view.EmployeeResponse;
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
    private final UserOfSendingRequest employeeOfSendingRequest;
    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;

    @Override
    public List<EmployeeResponse> getAllEmployees() {
        List<EmployeeResponse> employeeResponse;
        try {
            log.info("EmployeeServiceImpl:getAllEmployees execution started");
            List<Employee> employees = myEmployeeRepo.findAllEmployees()
                    .orElseThrow(() -> new CustomNotFoundException("Employees not found in Database"));
            log.info("received response from Database");
            employeeResponse = employees
                    .stream()
                    .map(employeeMapper::entityToResponse)
                    .toList();
        } catch (CustomNotFoundException e) {
            throw e;
        } catch (RuntimeException e) {
            log.error("Error in getAllEmployees {}", e.getMessage());
            throw new EmployeeServiceException("Error occurred while trying to get all employees");
        }
        return employeeResponse;
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        EmployeeDTO employeeDTO;
        try {
            log.info("EmployeeServiceImpl:getEmployeeById execution started");
            Employee employee = employeeRepository.findEmployeeWithRolesAndEmployeeDetailById(id)
                    .orElseThrow(() -> new CustomNotFoundException("Employees not found id: " + id));

            log.info("received employee from Database: {}", employee);
            employeeDTO = employeeMapper.entityToDTO(employee);
        } catch (CustomNotFoundException e) {
            throw e;
        } catch (RuntimeException e) {
            log.error("Error in getEmployeeById {}", e.getMessage());
            throw new EmployeeServiceException("Error occurred while trying to get employee with id: " + id);
        }
        log.info("EmployeeService:getEmployee execution ended");
        return employeeDTO;
    }

    @Override
    public EmployeeDTO getEmployee() {
        EmployeeDTO employeeDTO;
        try {
            log.info("EmployeeServiceImpl:getEmployee execution started");
            employeeDTO = getEmployeeById(employeeOfSendingRequest.getId());
        } catch (RuntimeException e) {
            log.error("Error in getEmployee {}", e.getMessage());
            throw new EmployeeServiceException("Error occurred while trying to get employee with id: " + employeeOfSendingRequest.getId());
        }
        log.info("EmployeeServiceImpl:getEmployee execution ended");
        return employeeDTO;
    }
}

