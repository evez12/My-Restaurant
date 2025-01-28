package com.huseynov.restaurant.cart.view;

import com.huseynov.restaurant.cart.CartItemService;
import com.huseynov.restaurant.cart.CartService;
import com.huseynov.restaurant.shared.dto.response.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/carts")
@RequiredArgsConstructor
@Slf4j
public class CartItemController {
    private final CartItemService cartItemService;
    private final CartService cartService;

    @PostMapping("/items")
    ResponseEntity<ApiResponse<CartDTO>> addItemToCart(@RequestParam Long productId,
                                                       @RequestParam
                                                       @Min(value = 1, message = "Quantity must be greater than 0")
                                                       int quantity) {

        log.info("CartItemController::addItemToCart, productId: {}, quantity: {}", productId, quantity);

        CartDTO cart = cartItemService.addItemToCart(productId, quantity);
        ApiResponse<CartDTO> response = new ApiResponse<>("Item successfully added to cart", cart);
        log.info("CartItemController:addItemToCart response: {}", response);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/items")
    ResponseEntity<ApiResponse<CartDTO>> updateItemQuantity(@RequestParam Long itemId,
                                                            @Valid
                                                            @RequestParam
                                                            @Min(value = 0, message = "Quantity must be greater than -1")
                                                            int quantity) {

        log.info("CartItemController::updateItemQuantity, itemId: {}, quantity: {}", itemId, quantity);

        cartService.checkCustomerForCart();

        CartDTO cartDTO = cartItemService.updateItemQuantity(itemId, quantity);

        ApiResponse<CartDTO> response = new ApiResponse<>("Item quantity successfully updated", cartDTO);
        log.info("CartItemController:updatedItemQuantity response: {}", response);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/items/{id}")
    ResponseEntity<ApiResponse<CartDTO>> removeItemFromCart(@PathVariable Long id) {


        log.info("CartItemController::removeItemFromCart , :  itemId: {}", id);

        cartService.checkCustomerForCart();

        CartDTO cartDTO = cartItemService.removeItemFromCart(id);

        ApiResponse<CartDTO> response = new ApiResponse<>("Item successfully removed from cart", cartDTO);
        log.info("CartItemController:removeItemFromCart response: {}", response);
        return ResponseEntity.ok(response);
    }


}
