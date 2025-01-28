package com.huseynov.restaurant.order;

import com.huseynov.restaurant.shared.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("${api.prefix}/orders")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j()
public class OrderController {
    private final OrderService orderService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<OrderDTO>> createOrder() {
        log.info("OrderController:createOrder execution started");
        OrderDTO order = orderService.createOrder();
        ApiResponse<OrderDTO> response = ApiResponse.<OrderDTO>builder()
                .status("Created order successfully")
                .results(order)
                .build();

        log.info("OrderController:createOrder ended, response: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDTO>> getOrder(@PathVariable(name = "id") Long id) {
        log.info("OrderController:getOrder execution started, orderId: {}", id);

        orderService.checkCustomerForOrder(id);

        OrderDTO order = orderService.getOrderDTOById(id);
        ApiResponse<OrderDTO> response = ApiResponse.<OrderDTO>builder()
                .status("Get order successfully")
                .results(order)
                .build();

        log.info("OrderController:getOrder execution ended, response: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getOrders() {
        log.info("OrderController:getOrder execution started");
        List<OrderDTO> order = orderService.getOrders();

        ApiResponse<List<OrderDTO>> response = ApiResponse.<List<OrderDTO>>builder()
                .status("Get all orders successfully")
                .results(order)
                .build();

        log.info("OrderController:getOrder response: {}", response);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<OrderDTO>> changeOrderStatus(@PathVariable(name = "id") Long orderId) {
        log.info("OrderController:cancelOrder execution started, cartId: {}", orderId);

        orderService.checkCustomerForOrder(orderId);

        OrderDTO order = orderService.cancelOrder(orderId);
        ApiResponse<OrderDTO> response = ApiResponse.<OrderDTO>builder()
                .status("Cancel order successfully")
                .results(order)
                .build();
        log.info("OrderController:changeOrderStatus execution ended, response: {}", response);
        return ResponseEntity.ok(response);
    }

}
