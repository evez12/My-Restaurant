package com.huseynov.restaurant.customer.data;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsCustomerByEmail(String email);

    @EntityGraph(value = "customer-role", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Customer> findCustomerByEmail(String email);
}
