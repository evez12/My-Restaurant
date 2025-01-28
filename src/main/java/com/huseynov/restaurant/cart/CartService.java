package com.huseynov.restaurant.cart;

public interface CartService {

    Cart initializeNewCart();

    Cart getCart();

    Cart getCartById(Long id);

    Cart saveCart(Cart cart);

    void deleteCart();

    void deleteCartById(Long id);

    void clearCart();

    void clearCartById(Long id);

    boolean isCustomerRequestValidForCart();

    void checkCustomerForCart();

    Long getCartId();

}































