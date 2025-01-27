package com.huseynov.restaurant.customer;

import com.huseynov.restaurant.customer.data.Customer;
import com.huseynov.restaurant.customer.view.CustomerDTO;
import com.huseynov.restaurant.shared.dto.request.RegisterRequest;

import java.util.List;

public interface CustomerService {


    Customer getCustomerById(Long id);

    CustomerDTO getCustomerByEmail(String email);

    List<CustomerDTO> getAllCustomers();

    CustomerDTO getCustomer();

    CustomerDTO getCustomerWithRolesByEmail(String email);

    CustomerDTO getCustomerWithRolesById(Long id);

    CustomerDTO updateCustomer(RegisterRequest request);

    void saveCustomer(Customer customer);

}
