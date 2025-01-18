package com.huseynov.restaurant.cart;

import com.huseynov.restaurant.shared.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/carts")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final CartService cartService;
    private final ModelMapper modelMapper;


    @GetMapping("/")
    public ResponseEntity<ApiResponse<CartDTO>> getCart() {
        log.info("CartController::getCart");

        cartService.checkCustomerForCart();  // Customer of cart check

        CartDTO cart = modelMapper.map(cartService.getCart(), CartDTO.class);
        ApiResponse<CartDTO> response = new ApiResponse<>("Successfully get cart", cart);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/")
    public ResponseEntity<ApiResponse<CartDTO>> deleteCart() {
        log.info("CartController::deleteCart");

        cartService.checkCustomerForCart();

        cartService.clearCart();
        ApiResponse<CartDTO> response = new ApiResponse<>("Successfully deleted cart", null);
        return ResponseEntity.ok(response);
    }

}
