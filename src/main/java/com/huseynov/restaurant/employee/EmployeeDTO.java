package com.huseynov.restaurant.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    Long employeeId;
    String name;
    String surname;
    String email;
    String password;
}
