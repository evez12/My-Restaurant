package com.huseynov.restaurant.customer;

import com.huseynov.restaurant.customer.data.Customer;
import com.huseynov.restaurant.customer.data.CustomerRepository;
import com.huseynov.restaurant.customer.data.MyCustomerRepository;
import com.huseynov.restaurant.customer.view.CustomerDTO;
import com.huseynov.restaurant.shared.UserOfSendingRequest;
import com.huseynov.restaurant.shared.dto.request.RegisterRequest;
import com.huseynov.restaurant.shared.exception.CustomNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepo;
    private final MyCustomerRepository myCustomerRepository;
    private final UserOfSendingRequest userOfSendingRequest;
    private final ModelMapper modelMapper;

    @Override
    public Customer getCustomerById(Long id) {
        try {
            log.info("CustomerServiceImpl:getCustomerById execution started");
            return customerRepo
                    .findById(id)
                    .orElseThrow(() -> new CustomNotFoundException("Customer not found, id: " + id));
        } catch (CustomNotFoundException e) {
            throw e;
        } catch (RuntimeException e) {
            log.error("Exception occurred while employee retrieving id: {} from Database, Exception message: {}", id, e.getMessage());
            throw new CustomerServiceException("Exception occurred while fetch employee from database id: " + id);
        }
    }

    @Override
    public CustomerDTO getCustomerByEmail(String email) {
        CustomerDTO customerDTO;
        try {
            log.info("CustomerServiceImpl:getCustomerByEmail execution started, email: {}", email);
            Customer customer = customerRepo
                    .findCustomerByEmail(email)
                    .orElseThrow(() -> new CustomNotFoundException("Customer not found, email: " + email));

            log.debug("CustomerServiceImpl:getCustomerByEmail, received response from Database: {}", customer);
            customerDTO = modelMapper.map(customer, CustomerDTO.class);
        } catch (CustomNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Exception occurred while retrieving customer email: {} from Database,  Exception message: {}", userOfSendingRequest.getEmail(), e.getMessage());
            throw new CustomerServiceException("Exception occurred while fetch customer from Database email: " + userOfSendingRequest.getEmail());
        }
        log.info("CustomerServiceImpl:getCustomerByEmail execution ended");
        return customerDTO;

    }

    @Override
    public List<CustomerDTO> getAllCustomers() {

        List<CustomerDTO> customerDTOs;
        try {
            log.info("CustomerServiceImpl:getAllCustomers execution started,");
            List<Customer> customers = myCustomerRepository.findAllCustomers().orElseThrow(
                    () -> new CustomNotFoundException("Customers not found")
            );
            customerDTOs = customers
                    .stream()
                    .map(customer -> modelMapper.map(customer, CustomerDTO.class))
                    .toList();
        } catch (CustomNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Exception occurred while retrieving customer : {} from Database, Exception message: {}", userOfSendingRequest.getId(), e.getMessage());
            throw new CustomerServiceException("Exception occurred while fetch customer from Database : " + userOfSendingRequest.getId());
        }
        log.info("CustomerServiceImpl:getAllCustomers execution ended");
        return customerDTOs;

    }

    @Override
    public CustomerDTO getCustomer() {
        CustomerDTO customerDTO;
        Long id = userOfSendingRequest.getId();
        try {
            log.info("CustomerServiceImpl:getCustomer execution started");
            customerDTO = getCustomerWithRolesById(id);
            log.debug("CustomerServiceImpl:getCustomer, received response: {}", customerDTO);
        } catch (CustomNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Exception occurred while retrieving customer id: {} from Database,  Exception message: {}", id, e.getMessage());
            throw new CustomerServiceException("Exception occurred while fetch customer from Database id: " + id);
        }
        log.info("CustomerServiceImpl:getCustomer execution ended");
        return customerDTO;

    }

    @Override
    public CustomerDTO getCustomerWithRolesByEmail(String email) {
        CustomerDTO customerDTO;
        try {
            log.info("CustomerServiceImpl:getCustomerWithRolesByEmail execution started, email: {}", email);
            Customer customer = myCustomerRepository.findCustomerWithRolesByEmail(email).orElseThrow(
                    () -> new CustomNotFoundException("Customer not found with email: " + email)
            );
            log.debug("CustomerServiceImpl:getCustomerWithRolesByEmail, received response from Database: {}", customer);
            customerDTO = modelMapper.map(customer, CustomerDTO.class);
        } catch (CustomNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Exception occurred while retrieving customer email: {} from Database, Exception message: {}", email, e.getMessage());
            throw new CustomerServiceException("Exception occurred while fetch customer from Database email: " + email);
        }
        log.info("CustomerServiceImpl:getCustomerWithRolesByEmail execution ended");
        return customerDTO;
    }

    @Override
    public CustomerDTO getCustomerWithRolesById(Long id) {
        CustomerDTO customerDTO;
        try {
            log.info("CustomerServiceImpl:getCustomerWithRolesById execution started, id: {}", id);
            Customer customer = myCustomerRepository
                    .findCustomerWithRolesById(id)
                    .orElseThrow(
                            () -> new CustomNotFoundException("Customer not found id: {}" + id)
                    );

            log.debug("CustomerServiceImpl:getCustomerWithRolesById, received response from Database: {}", customer);
            customerDTO = modelMapper.map(customer, CustomerDTO.class);

        } catch (CustomNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Exception occurred while retrieving customer id: {} from Database, Exception message: {}", id, e.getMessage());
            throw new CustomerServiceException("Exception occurred while fetch customer from Database id: " + id);
        }
        log.info("CustomerServiceImpl:getCustomerWithRolesById execution ended");
        return customerDTO;
    }

    @Override
    public CustomerDTO updateCustomer(RegisterRequest request) {
//        Customer customer = getCustomer(); // customer of sending request

        return null;
    }

    @Override
    public void saveCustomer(Customer customer) {
        try {
            log.info("CustomerServiceImpl:saveCustomer execution started");
            customerRepo.save(customer);
        } catch (Exception e) {
            log.error("Exception occurred while saving customer to Database gmail: {}, Exception message: {}", customer.getEmail(), e.getMessage());
            throw new CustomerServiceException("Exception occurred while employee saving to Database email: " + customer.getEmail());
        }
    }

}
