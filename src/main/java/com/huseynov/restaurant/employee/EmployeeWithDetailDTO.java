package com.huseynov.restaurant.employee;

import com.huseynov.restaurant.shared.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeWithDetailDTO {
    EmployeeDTO employeeDTO;
    Long detailId;
    String address;
    boolean enabled;
    Gender gender;
    BigDecimal salary = BigDecimal.ZERO;
    String phoneNumber;

    public EmployeeWithDetailDTO(Long detailId, String address, boolean enabled,
                                 Gender gender, BigDecimal salary, String phoneNumber) {
        this.detailId = detailId;
        this.address = address;
        this.enabled = enabled;
        this.gender = gender;
        this.salary = salary;
        this.phoneNumber = phoneNumber;
    }
}
