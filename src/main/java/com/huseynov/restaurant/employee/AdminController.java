package com.huseynov.restaurant.employee;

import com.huseynov.restaurant.customer.Customer;
import com.huseynov.restaurant.customer.CustomerDTO;
import com.huseynov.restaurant.customer.CustomerService;
import com.huseynov.restaurant.shared.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("${api.prefix}/admin")
@CrossOrigin()
@RequiredArgsConstructor
@Slf4j()
public class AdminController {
    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    @GetMapping("/customers")
    public ResponseEntity<ApiResponse<List<CustomerDTO>>> getAllCustomers() {
        log.info("CustomerController::getAllCustomers");

        List<Customer> customersDB = customerService.getAllCustomers();

        List<CustomerDTO> customers = customersDB.stream()
                .map(customer -> modelMapper.map(customer, CustomerDTO.class))
                .toList();

        ApiResponse<List<CustomerDTO>> response = new ApiResponse<>("Successfully get all customers", customers);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/customers/{id}")
    public ResponseEntity<ApiResponse<CustomerDTO>> getCustomerById(@PathVariable Long id) {
        log.info("CustomerController::getCustomerById");

        CustomerDTO customer = modelMapper.map(customerService.getCustomerById(id), CustomerDTO.class);
        ApiResponse<CustomerDTO> response = new ApiResponse<>("Successfully get customer by id", customer);

        return ResponseEntity.ok(response);
    }


}
