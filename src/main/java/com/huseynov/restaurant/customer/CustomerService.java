package com.huseynov.restaurant.customer;

import java.util.List;

public interface CustomerService {


    Customer getCustomerById(Long id);

    boolean isEnableCustomer(Customer customer);

    boolean isDisableCustomer(Customer customer);

    Customer getCustomerByEmail(String email);

    List<Customer> getAllCustomers();

    Customer getCustomer();

    Customer getCustomerWithRolesByEmail(String email);

    Customer getCustomerWithRolesById(Long id);


    void saveCustomer(Customer customer);

}
