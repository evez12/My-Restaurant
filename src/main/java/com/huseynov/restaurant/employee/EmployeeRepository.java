package com.huseynov.restaurant.employee;

import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsEmployeeByEmail(@NonNull String email);

    @Query("Select e from Employee e join fetch  e.roles join fetch e.employeeDetail where e.email=:email")
    Optional<Employee> findEmployeeByEmail(@NonNull String email);

    @EntityGraph(value = "employee-detail-graph", type = EntityGraph.EntityGraphType.LOAD)
    @NonNull
    List<Employee> findAll();

}
