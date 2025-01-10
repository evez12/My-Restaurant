package com.huseynov.restaurant.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsCustomerByEmail(String email);

    @Query("SELECT c FROM Customer c join fetch c.roles WHERE c.email = :email")
    Optional<Customer> findCustomerByEmail(String email);
}
