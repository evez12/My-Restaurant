package com.huseynov.restaurant.customer;

import com.huseynov.restaurant.shared.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/customer")
@RequiredArgsConstructor
@Slf4j
class CustomerController {
    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<CustomerDTO>> getCustomerProfile() {
        log.info("CustomerController::getCustomerProfile");

        CustomerDTO customer = modelMapper.map(customerService.getCustomer(), CustomerDTO.class);
        ApiResponse<CustomerDTO> response = new ApiResponse<>("Successfully get customer", customer);

        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<CustomerDTO>> getCustomer() {
        log.info("CustomerController::getCustomer");

        CustomerDTO customer = modelMapper.map(customerService.getCustomer(), CustomerDTO.class);
        ApiResponse<CustomerDTO> response = new ApiResponse<>("Successfully get customer's profile", customer);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("")
    public ResponseEntity<ApiResponse<CustomerDTO>> updatedCustomer(){
        log.info("CustomerController::updatedCustomer");
//        CustomerDTO customer = modelMapper.map(customerService.)
        return null;
    }


}
