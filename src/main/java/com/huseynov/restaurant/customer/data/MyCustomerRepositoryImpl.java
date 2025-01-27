package com.huseynov.restaurant.customer.data;

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

@Slf4j
@Repository
@RequiredArgsConstructor
public class MyCustomerRepositoryImpl implements MyCustomerRepository {
    private final EntityManager entityManager;

    @Override
    public Optional<List<Customer>> findAllCustomers() {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
        Root<Customer> root = criteriaQuery.from(Customer.class);

        criteriaQuery.select(
                criteriaBuilder.construct(
                        Customer.class,
                        root.get("id"),
                        root.get("name"),
                        root.get("surname"),
                        root.get("email"),
                        root.get("password"),
                        root.get("enabled"),
                        root.get("phoneNumber"),
                        root.get("address"),
                        root.get("gender")
                )
        );
        TypedQuery<Customer> query = entityManager.createQuery(criteriaQuery);

        try {
            return Optional.ofNullable(query.getResultList());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findCustomerWithRolesByEmail(String email) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
        Root<Customer> root = criteriaQuery.from(Customer.class);

        criteriaQuery.select(
                criteriaBuilder.construct(
                        Customer.class,
                        root.get("id"),
                        root.get("name"),
                        root.get("surname"),
                        root.get("email"),
                        root.get("password"),
                        root.get("enabled"),
                        root.get("phoneNumber"),
                        root.get("address"),
                        root.get("gender"),
                        root.get("roles")
                )
        ).where(criteriaBuilder.equal(root.get("email"), email));
        TypedQuery<Customer> query = entityManager.createQuery(criteriaQuery);

        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }

    }

    @Override
    public Optional<Customer> findCustomerWithRolesById(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
        Root<Customer> root = criteriaQuery.from(Customer.class);

        criteriaQuery.select(
                criteriaBuilder.construct(
                        Customer.class,
                        root.get("id"),
                        root.get("name"),
                        root.get("surname"),
                        root.get("email"),
                        root.get("password"),
                        root.get("enabled"),
                        root.get("phoneNumber"),
                        root.get("address"),
                        root.get("gender"),
                        root.get("roles")
                )
        ).where(criteriaBuilder.equal(root.get("id"), id));
        TypedQuery<Customer> query = entityManager.createQuery(criteriaQuery);

        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
