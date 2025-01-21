package com.huseynov.restaurant.customer;


import java.util.List;
import java.util.Optional;

public interface MyCustomerRepository {

    Optional<List<Customer>> findAllCustomers();

    Optional<Customer> findCustomerWithRolesByEmail(String email);

    Optional<Customer> findCustomerWithRolesById(Long id);
}
