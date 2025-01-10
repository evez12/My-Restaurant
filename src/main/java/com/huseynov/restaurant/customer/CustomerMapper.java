package com.huseynov.restaurant.customer;

import com.huseynov.restaurant.shared.dto.request.RegisterRequest;

public class CustomerMapper {
    private CustomerMapper() {
    }

    public static Customer convertRegisterrequestToCustomer(RegisterRequest request) {

        Customer customer = new Customer();
        customer.setEmail(request.getEmail());
        customer.setName(request.getName());
        customer.setSurname(request.getSurname());
        customer.setPassword(request.getPassword());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setAddress(request.getAddress());
        customer.setGender(request.getGender());

        return customer;
    }
}
