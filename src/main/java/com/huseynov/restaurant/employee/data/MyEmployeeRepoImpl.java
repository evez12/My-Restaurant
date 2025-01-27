package com.huseynov.restaurant.employee.data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class MyEmployeeRepoImpl implements MyEmployeeRepo {
    private final EntityManager entityManager;

    @Override
    public Optional<List<Employee>> findAllEmployees() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> root = criteriaQuery.from(Employee.class);
        criteriaQuery.select(
                criteriaBuilder.construct(
                        Employee.class,
                        root.get("id"),
                        root.get("email"),
                        root.get("name"),
                        root.get("surname")
                )
        );

        try {
            TypedQuery<Employee> query = entityManager.createQuery(criteriaQuery);
            return Optional.ofNullable(query.getResultList());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

}
