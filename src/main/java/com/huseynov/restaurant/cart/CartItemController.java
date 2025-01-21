package com.huseynov.restaurant.cart;

import com.huseynov.restaurant.shared.dto.response.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/carts")
@RequiredArgsConstructor
@Slf4j
public class CartItemController {
    private final CartItemService cartItemService;
    private final CartService cartService;
    private final ModelMapper modelMapper;


    @PostMapping("/items")
    ResponseEntity<ApiResponse<CartDTO>> addItemToCart(@RequestParam Long productId,
                                                       @RequestParam
                                                       @Min(value = 1, message = "Quantity must be greater than 0")
                                                       int quantity) {

        log.info("CartItemController::addItemToCart, productId: {}, quantity: {}", productId, quantity);

        // if the customer who sent request is not the owner of the cart(for exists cart)
        cartService.checkCustomerForCart();

        CartDTO cart = modelMapper
                .map(cartItemService.addItemToCart(productId, quantity), CartDTO.class);
        ApiResponse<CartDTO> response = new ApiResponse<>("Item successfully added to cart", cart);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/items/{id}")
    ResponseEntity<ApiResponse<CartDTO>> removeItemFromCart(@PathVariable Long id) {


        log.info("CartItemController::removeItemFromCart , :  itemId: {}", id);

        cartService.checkCustomerForCart();

        CartDTO cartDTO = modelMapper
                .map(cartItemService.removeItemFromCart(id), CartDTO.class);

        ApiResponse<CartDTO> response = new ApiResponse<>("Item successfully removed from cart", cartDTO);
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

        CartDTO cartDTO = modelMapper
                .map(cartItemService.updateItemQuantity(itemId, quantity), CartDTO.class);

        ApiResponse<CartDTO> response = new ApiResponse<>("Item quantity successfully updated", cartDTO);
        return ResponseEntity.ok(response);
    }


}
