package com.huseynov.restaurant.customer;

import com.huseynov.restaurant.shared.exception.CustomAuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepo;
    private final MyCustomerRepository myCustomerRepository;


    @Override
    public Customer getCustomerById(Long id) {
        log.info("CustomerServiceImpl:getCustomerById execution started");
        return customerRepo
                .findById(id)
                .orElseThrow(() -> new CustomAuthException("Customer not found, id: " + id));

    }

    @Override
    public boolean isEnableCustomer(Customer customer) {
        return customer.isEnabled();
    }

    @Override
    public boolean isDisableCustomer(Customer customer) {
        return customer.isDisabled();
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        log.info("CustomerServiceImpl:getCustomerByEmail execution started");
        return customerRepo
                .findCustomerByEmail(email)
                .orElseThrow(() -> new CustomAuthException("Customer not found, email: " + email));
    }

    @Override
    public List<Customer> getAllCustomers() {
        log.info("CustomerServiceImpl:getAllCustomers execution");
        return myCustomerRepository.findAllCustomers().orElseThrow(
                () -> new CustomAuthException("Customers not found")
        );
    }

    @Override
    public Customer getCustomer() {
        return null;
    }

    @Override
    public Customer getCustomerWithRolesByEmail(String email) {
        log.info("CustomerServiceImpl:getCustomerWithRolesByEmail execution started, email: {}", email);

        return myCustomerRepository.findCustomerWithRolesByEmail(email).orElseThrow(
                () -> new CustomAuthException("Customer not found with email: " + email)
        );
    }

    @Override
    public Customer getCustomerWithRolesById(Long id) {
        log.info("CustomerServiceImpl:getCustomerWithRolesById execution started, id: {}", id);

        return myCustomerRepository.findCustomerWithRolesById(id).orElseThrow(
                () -> new CustomAuthException("Customer not found with id: " + id)
        );

    }

    @Override
    public void saveCustomer(Customer customer) {
        log.info("CustomerServiceImpl:saveCustomer execution started");
        customerRepo.save(customer);
    }

}
