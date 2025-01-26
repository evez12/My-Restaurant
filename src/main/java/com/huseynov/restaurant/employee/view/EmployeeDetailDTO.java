package com.huseynov.restaurant.employee.view;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EmployeeDetailDTO {
    Long detailId;
    String address;
    boolean enabled = Boolean.TRUE;
    BigDecimal salary = BigDecimal.ZERO;
    String phoneNumber;
}
