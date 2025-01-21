package com.huseynov.restaurant.shared;

import com.huseynov.restaurant.customer.Customer;
import com.huseynov.restaurant.customer.CustomerDTO;
import com.huseynov.restaurant.customer.MyCustomerRepository;
import com.huseynov.restaurant.shared.exception.CustomNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("${api.prefix}")
@RequiredArgsConstructor
public class TestController {
    private final MyCustomerRepository myCustomerRepository;
    private final ModelMapper modelMapper;

    @GetMapping("/hello/test")
    public String hello() {
        return "Hello everyone";
    }

    @GetMapping("/hello/email/{email}")
    public ResponseEntity<CustomerDTO> getCustomerByEmail(@PathVariable String email) {

        log.info("TestController::getCustomer, email: {}", email);
        Customer customer = myCustomerRepository.findCustomerWithRolesByEmail(email).orElseThrow(
                () -> new CustomNotFoundException("Customer not found with email: " + email));

        CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);

        return ResponseEntity.ok(customerDTO);
    }

    @GetMapping("/hello/id/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {

        log.info("TestController::getCustomer, id: {}", id);
        Customer customer = myCustomerRepository.findCustomerWithRolesById(id).orElseThrow(
                () -> new CustomNotFoundException("Customer not found with id: " + id));

        CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);

        return ResponseEntity.ok(customerDTO);
    }

    @GetMapping("/admin/test")
    public String admin() {
        return "Admin page";
    }

    @GetMapping("/customer/test")
    public String customer() {
        return "Customer page";
    }


    @GetMapping("/manager/test")
    public String manager() {
        return "Manager page";
    }

    @GetMapping("/employee/test")
    public String employee() {
        return "Employee page";
    }
}
