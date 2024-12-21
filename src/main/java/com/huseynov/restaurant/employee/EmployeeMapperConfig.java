package com.huseynov.restaurant.employee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmployeeMapperConfig {
    private final ModelMapper modelMapper;

    public EmployeeMapperConfig(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Bean
    ModelMapper employeeMapper() {

        //   Define the mapping for Employee to EmployeeResponse
        modelMapper.addMappings(new PropertyMap<Employee, EmployeeResponse>() {
            @Override
            protected void configure() {
                map().setEmployeeId(source.getId());
                map().setName(source.getName());
                map().setSurname(source.getSurname());
                map().setEmail(source.getEmail());
                map().setAddress(source.getEmployeeDetail().getAddress());
                map().setPhoneNumber(source.getEmployeeDetail().getPhoneNumber());
                map().setGender(source.getEmployeeDetail().getGender());
                map().setSalary(source.getEmployeeDetail().getSalary());
            }
        });

        // Define the mapping for Employee to EmployeeResponse
        modelMapper.addMappings(new PropertyMap<CreateEmployeeRequest, Employee>() {
            @Override
            protected void configure() {
                map().setName(source.getName());
                map().setSurname(source.getSurname());
                map().setEmail(source.getEmail());
                map().getEmployeeDetail().setAddress(source.getAddress());
                map().getEmployeeDetail().setPhoneNumber(source.getPhoneNumber());
                map().getEmployeeDetail().setGender(source.getGender());
                map().getEmployeeDetail().setSalary(source.getSalary());
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

    public Employee convertDtoToEntity(CreateEmployeeRequest employeeRequest) {
        return modelMapper.map(employeeRequest, Employee.class);
    }

    public String jsonAsString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
