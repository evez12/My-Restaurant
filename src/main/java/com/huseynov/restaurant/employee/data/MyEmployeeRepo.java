package com.huseynov.restaurant.employee.data;

import java.util.List;
import java.util.Optional;

public interface MyEmployeeRepo {
    Optional<List<Employee>> findAllEmployees();

}
