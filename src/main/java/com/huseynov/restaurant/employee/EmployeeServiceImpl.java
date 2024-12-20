package com.huseynov.restaurant.employee;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees
                .stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<EmployeeWithDetailDTO> getAllEmployeesWithDetail() {
        List<Employee> employees = employeeRepository.findAll();
        return employees
                .stream()
                .map(this::convertEntityToDTOWithDetail)
                .collect(Collectors.toList());
    }

    @Override
    public Employee getEmployeeById(Long id) {
        EmployeeWithDetailDTO employeeWithDetailDTO = new EmployeeWithDetailDTO();

        Employee employee = employeeRepository.
                findById(id).orElseThrow(() -> new RuntimeException("Not Found Employee with id: " + id));
        return null;
    }

    public EmployeeDTO convertEntityToDTO(Employee employee) {
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    public EmployeeWithDetailDTO convertEntityToDTOWithDetail(Employee employee) {
        return modelMapper.map(employee, EmployeeWithDetailDTO.class);
    }

}
