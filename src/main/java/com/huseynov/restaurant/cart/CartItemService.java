package com.huseynov.restaurant.cart;

import java.util.Optional;

public interface CartItemService {

    Cart addItemToCart(Long productId, int quantity);

    Optional<Cart> removeItemFromCart(Long itemId);

    Optional<Cart> updateItemQuantity(Long itemId, int quantity);


}
