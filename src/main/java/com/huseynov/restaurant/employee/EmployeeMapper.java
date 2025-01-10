package com.huseynov.restaurant.employee;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
class EmployeeMapper {
    private final ModelMapper modelMapper;

    public EmployeeMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static Employee convertCreateEmployeeRequestToEmployee(CreateEmployeeRequest request) {
        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setSurname(request.getSurname());
        employee.setEmail(request.getEmail());
        EmployeeDetail employeeDetail = new EmployeeDetail();
        employeeDetail.setAddress(request.getAddress());
        employeeDetail.setPhoneNumber(request.getPhoneNumber());
        employeeDetail.setGender(request.getGender());
        employeeDetail.setSalary(request.getSalary());
        employee.setEmployeeDetail(employeeDetail);
        log.info("EmployeeMapper::convertCreateEmployeeRequestToEmployee employee {}", employee);
        log.info("EmployeeMapper::convertCreateEmployeeRequestToEmployee employeeDetail {}", employee.getEmployeeDetail());
        return employee;
    }

    ModelMapper employeeMapper() {

        //   Define the mapping for Employee to EmployeeResponse
        modelMapper.addMappings(new PropertyMap<Employee, EmployeeResponse>() {
            @Override
            protected void configure() {
                map().setName(source.getName());
                map().setSurname(source.getSurname());
                map().setEmail(source.getEmail());
                map().setAddress(source.getEmployeeDetail().getAddress());
                map().setPhoneNumber(source.getEmployeeDetail().getPhoneNumber());
                map().setGender(source.getEmployeeDetail().getGender());
                map().setSalary(source.getEmployeeDetail().getSalary());
            }
        });

        return modelMapper;
    }

    public EmployeeDTO convertEntityDTO(Employee employee) {
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    public Employee convertDtoToEntity(EmployeeDTO employeeDTO) {
        return modelMapper.map(employeeDTO, Employee.class);
    }

    public EmployeeResponse convertEntityToResponse(Employee employee) {
        return modelMapper.map(employee, EmployeeResponse.class);
    }

    public Employee convertDtoToEntity(EmployeeResponse employeeResponse) {
        return modelMapper.map(employeeResponse, Employee.class);
    }

}
