package com.huseynov.restaurant.cart;

import com.huseynov.restaurant.shared.dto.response.ApiResponse;
import com.huseynov.restaurant.shared.exception.InvalidRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/carts")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final CartService cartService;
    private final ModelMapper modelMapper;


    @GetMapping("/")
    public ResponseEntity<ApiResponse<CartDTO>> getCart() {

        // Check if the customer request is valid for the cart(If the customer is the owner of the cart)
        if (!cartService.isCustomerRequestValidForCart()) {
            throw new InvalidRequestException("Invalid request for cart");
        }

        log.info("CartController::getCart");
        CartDTO cart = modelMapper.map(cartService.getCart(), CartDTO.class);
        ApiResponse<CartDTO> response = new ApiResponse<>("Successfully get cart ", cart);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/")
    public ResponseEntity<ApiResponse<CartDTO>> deleteCart() {

        if (!cartService.isCustomerRequestValidForCart()) {
            throw new InvalidRequestException("Invalid request for cart");
        }

        log.info("CartController::deleteCart");
        cartService.clearCart();
        ApiResponse<CartDTO> response = new ApiResponse<>("Successfully deleted cart" , null);
        return ResponseEntity.ok(response);
    }


}
