package com.huseynov.restaurant.employee;

import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsEmployeeByEmail(@NonNull String email);

    @EntityGraph(value = "employee-detail", type = EntityGraph.EntityGraphType.LOAD)
    @NonNull
    List<Employee> findAll();

}
