package com.huseynov.restaurant.customer;

import com.huseynov.restaurant.security.CustomerLoginResponse;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsCustomerByEmail(String email);

    @EntityGraph(value = "customer-role", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Customer> findCustomerByEmail(String email);
//    @Query("SELECT c.id,c.email,c.password,c.roles FROM Customer c JOIN FETCH c.roles WHERE c.email = :email")
//    Optional<CustomerLoginResponse> findCustomerByEmail(String email);


}
