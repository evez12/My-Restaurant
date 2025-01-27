package com.huseynov.restaurant.customer.view;

import com.huseynov.restaurant.customer.CustomerService;
import com.huseynov.restaurant.shared.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("${api.prefix}/admin")
@CrossOrigin()
@RequiredArgsConstructor
@Slf4j()
public class AdminControllerC {
    private final CustomerService customerService;

    @GetMapping("/customers")
    public ResponseEntity<ApiResponse<List<CustomerDTO>>> getAllCustomers() {
        log.info("AdminControllerC::getAllCustomers execution started");

        List<CustomerDTO> customers = customerService.getAllCustomers();
        ApiResponse<List<CustomerDTO>> response = new ApiResponse<>("Successfully get all customers", customers);
        log.info("AdminControllerC:getAllCustomers response: {}", response);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<ApiResponse<CustomerDTO>> getCustomerById(@PathVariable Long id) {
        log.info("AdminControllerC::getCustomerById execution started, id: {}", id);

        CustomerDTO customer = customerService.getCustomerWithRolesById(id);
        ApiResponse<CustomerDTO> response = new ApiResponse<>("Successfully get customer by id", customer);
        log.info("AdminControllerC::getCustomerById response: {}", response);

        return ResponseEntity.ok(response);
    }

}
