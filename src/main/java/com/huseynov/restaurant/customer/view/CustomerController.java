package com.huseynov.restaurant.customer.view;

import com.huseynov.restaurant.customer.CustomerService;
import com.huseynov.restaurant.shared.dto.request.RegisterRequest;
import com.huseynov.restaurant.shared.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/customer")
@RequiredArgsConstructor
@Slf4j
class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<CustomerDTO>> getCustomerProfile() {
        log.info("CustomerController::getCustomerProfile");

        CustomerDTO customer = customerService.getCustomer();
        ApiResponse<CustomerDTO> response = new ApiResponse<>("Successfully get customer's profile", customer);

        log.info("CustomerController:getCustomerProfile response: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<CustomerDTO>> getCustomer() {
        log.info("CustomerController::getCustomer");

        CustomerDTO customer = customerService.getCustomer();
        ApiResponse<CustomerDTO> response = new ApiResponse<>("Successfully get customer", customer);

        log.info("CustomerController:getCustomer response: {}", response);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("")
    public ResponseEntity<ApiResponse<CustomerDTO>> updateCustomer(@RequestBody RegisterRequest updateCustomer) {
        log.info("CustomerController::updatedCustomer, update customer: {}", updateCustomer);

//        CustomerDTO customer = modelMapper.map(customerService.)
        return null;
    }


}
