package com.huseynov.restaurant.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapperConfig employeeMapper;

    @Override
    public EmployeeResponse createEmployee(CreateEmployeeRequest request) {

        if (employeeRepository.existsEmployeeByEmail(request.getEmail())) {
            throw new ExistsEmailException("Email already exists");
        }


        Employee employee = employeeMapper.convertDtoToEntity(request);
        EmployeeDetail employeeDetail = employee.getEmployeeDetail();


        if (employeeDetail != null) {
            employeeDetail.setEmployee(employee);
        }

        Employee savedEmployee = employeeRepository.save(employee);

        return employeeMapper.convertEntityToResponse(savedEmployee);
    }

    @Override
    public List<EmployeeResponse> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees
                .stream()
                .map(employeeMapper::convertEntityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {
//        EmployeeResponse employeeResponse = new EmployeeResponse();

//        Employee employee = employeeRepository.
//                findById(id).orElseThrow(() -> new RuntimeException("Not Found Employee with id: " + id));
        return null;
    }

}
