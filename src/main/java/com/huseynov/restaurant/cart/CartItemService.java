package com.huseynov.restaurant.cart;

public interface CartItemService {

    CartDTO addItemToCart(Long productId, int quantity);

    CartDTO removeItemFromCart(Long itemId);

    CartDTO updateItemQuantity(Long itemId, int quantity);

}
